package controladores;

import java.time.ZoneId;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import logica.MantenimientoManagerFactory;
import modelo.Mantenimiento;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javax.ws.rs.core.GenericType;
import logica.VehiculoManagerFactory;
import modelo.Vehiculo;

public class EditingCell<T> extends TableCell<Mantenimiento, T> {

    private TextField textField;
    private CheckBox checkBox;
    private DatePicker datePicker;
    private ComboBox<Long> comboBox; // Para el ComboBox de idVehiculo

    public EditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            T item = getItem();
            if (item instanceof String) {
                createTextField();
            } else if (item instanceof Boolean) {
                createCheckBox();
            } else if (item instanceof Date) {
                createDatePicker();
            } else if (item instanceof Long) {  // Agregar esta condición para ComboBox
                createComboBox();  // Crear el ComboBox para idVehiculo
            }

            setText(null);
            setGraphic(item instanceof String ? textField
                    : item instanceof Boolean ? checkBox
                            : item instanceof Date ? datePicker
                                    : item instanceof Long ? comboBox : null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (item instanceof String) {
                    textField.setText(getString());
                    setGraphic(textField);
                } else if (item instanceof Boolean) {
                    checkBox.setSelected((Boolean) item);
                    setGraphic(checkBox);
                } else if (item instanceof Date) {
                    datePicker.setValue(((Date) item).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    setGraphic(datePicker);
                } else if (item instanceof Long) {
                     if (getTableRow().getItem() != null) {
                    Mantenimiento mantenimiento = (Mantenimiento) getTableRow().getItem();
                    if (mantenimiento.getIdVehiculo() == null) {
                        // Si el idVehiculo es null, mostrar ComboBox
                        createComboBox();
                        comboBox.setValue((Long) item);
                        setGraphic(comboBox);
                    } else {
                        // Si ya hay un idVehiculo asignado, mostrar un TextField
                        textField.setText(String.valueOf(item));
                        setGraphic(textField);
                    }
                }
                }
            } else {
                setText(getString());
                setGraphic(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction(event -> commitEdit((T) textField.getText()));
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
            if (!arg2) {
                commitEdit((T) textField.getText());
            }
        });
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private void createCheckBox() {
        checkBox = new CheckBox();
        checkBox.setSelected((Boolean) getItem()); // Aquí convertimos a Boolean
        checkBox.setOnAction(event -> commitEdit((T) Boolean.valueOf(checkBox.isSelected())));
        checkBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setValue(((Date) getItem()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        datePicker.setOnAction(event -> commitEdit((T) Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private void createComboBox() {
        comboBox = new ComboBox<>();
        // Aquí cargo los vehículos disponibles desde una lista
        comboBox.setItems(FXCollections.observableArrayList(obtenerIdVehiculosDisponibles()));
        comboBox.setOnAction(event -> {
            Long selectedId = comboBox.getValue();
            commitEdit((T) selectedId); // Cometer el valor seleccionado
        });
        comboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    @Override
    public void commitEdit(Object newValue) {
        // Obtener el mantenimiento de la fila
        Mantenimiento mantenimiento = (Mantenimiento) getTableRow().getItem();
        if (mantenimiento != null) {
            // Actualizar la propiedad correspondiente del objeto Mantenimiento según el tipo de campo
            if (newValue instanceof String) {
                mantenimiento.setDescripcion((String) newValue);
            } else if (newValue instanceof Boolean) {
                mantenimiento.setMantenimientoExitoso((Boolean) newValue);
            } else if (newValue instanceof Date) {
                mantenimiento.setFechaFinalizacion((Date) newValue);
            } else if (newValue instanceof Long) {
                mantenimiento.setIdVehiculo((Long) newValue);  // Actualizar el idVehiculo
            }

            // Persistir el cambio en el backend
            try {
                // Llamar al método para persistir los datos
                MantenimientoManagerFactory.get().edit_XML(mantenimiento, String.valueOf(mantenimiento.getIdMantenimiento()));

                // Mostrar mensaje de éxito en la interfaz
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.INFORMATION, "Mantenimiento actualizado correctamente.", ButtonType.OK).showAndWait();
                });
            } catch (Exception e) {
                // Si algo falla en la persistencia, muestra un mensaje de error
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Error al actualizar el mantenimiento en el servidor.", ButtonType.OK).showAndWait();
                });
                e.printStackTrace();
            }
        }

        // Llamar al commitEdit original para finalizar la edición
        super.commitEdit((T) newValue);
    }

    // Método para obtener la lista de idVehiculos disponibles
    private List<Long> obtenerIdVehiculosDisponibles() {
        List<Long> idVehiculos = new ArrayList<>();
        
        try {
            // Obtener todos los vehículos disponibles desde el backend
            List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>(){});
            
            // Extraer los IDs de los vehículos disponibles
            for (Vehiculo vehiculo : vehiculos) {
                idVehiculos.add(vehiculo.getIdVehiculo());
            }
        } catch (Exception e) {
            // Manejo de excepciones si no se pueden obtener los vehículos
            e.printStackTrace();
        }
        
        return idVehiculos;
    }
}
