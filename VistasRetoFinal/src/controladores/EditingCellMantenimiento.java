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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javax.ws.rs.core.GenericType;
import logica.VehiculoManagerFactory;
import modelo.Vehiculo;

public class EditingCellMantenimiento<T> extends TableCell<Mantenimiento, T> {

    // Atributos Vacios
    private TextField textField;
    private CheckBox checkBox;
    private DatePicker datePicker;
    private ChoiceBox<Long> choiceBox;

    // Constructor Vacio
    public EditingCellMantenimiento() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            T item = getItem();

            if (item instanceof String) {
                createTextField();
                textField.setText(getString());
                setGraphic(textField);
            } else if (item instanceof Boolean) {
                createCheckBox();
                checkBox.setSelected((Boolean) item);
                setGraphic(checkBox);
            } else if (item instanceof Date) {
                createDatePicker();
                datePicker.setValue(((Date) item).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                setGraphic(datePicker);
            } else if (item instanceof Long) {
                Mantenimiento mantenimiento = (Mantenimiento) getTableRow().getItem();
                if (mantenimiento != null && mantenimiento.getIdVehiculo() != null && mantenimiento.getIdVehiculo() == 0L) {
                    createChoiceBox();
                    choiceBox.setValue((Long) item);
                    setGraphic(choiceBox);
                } else {
                    cancelEdit();
                }
            } else {
                setText(getString());
                setGraphic(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
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
                    Mantenimiento mantenimiento = (Mantenimiento) getTableRow().getItem();
                    if (mantenimiento != null && mantenimiento.getIdVehiculo() != null && mantenimiento.getIdVehiculo() == 0L) {
                        choiceBox.setValue((Long) item);
                        setGraphic(choiceBox);
                    } else {
                        setText(getString());
                        setGraphic(null);
                        setContentDisplay(ContentDisplay.TEXT_ONLY);
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

        // Bandera para controlar si se canceló por ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        textField.setOnAction(event -> commitEdit((T) textField.getText()));

        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
            if (!arg2 && !presionarEsc.get()) {
                commitEdit((T) textField.getText());
            }
        });
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(true); // Marca la bandera
                cancelEdit();
            }
        });

        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(false); //Restablece la bandera al liberar ESC
            }
        });

    }

    private void createCheckBox() {
        checkBox = new CheckBox();
        checkBox.setSelected((Boolean) getItem());
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

    private void createChoiceBox() {
        choiceBox = new ChoiceBox<>();
        choiceBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // Obtener vehículos disponibles
        List<Long> idVehiculos = obtenerIdVehiculosDisponibles();
        if (idVehiculos.isEmpty()) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.WARNING, "No hay vehiculos disponibles", ButtonType.OK).showAndWait();
            });
        }

        // Cargar los vehículos disponibles en el ChoiceBox
        choiceBox.setItems(FXCollections.observableArrayList(idVehiculos));
        choiceBox.setOnAction(event -> {
            Long selectedId = choiceBox.getValue();
            if (selectedId != null && !selectedId.equals(getItem())) { // Verificar cambio
                commitEdit((T) selectedId);
            } else {
                cancelEdit(); // Si no cambia el valor, cancela la edición
            }
        });
        choiceBox.setOnKeyPressed(event -> {
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
        Mantenimiento mantenimiento = (Mantenimiento) getTableRow().getItem();
        if (mantenimiento != null) {
            if (newValue instanceof String) {
                // Validar que la descripción no tenga más de 50 caracteres
                if (((String) newValue).length() > 50) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.WARNING, "La descripción no puede tener más de 50 caracteres.", ButtonType.OK).showAndWait();
                    });
                    cancelEdit();
                    return; // Salir sin confirmar la edición
                }
                mantenimiento.setDescripcion((String) newValue);
            } else if (newValue instanceof Boolean) {
                mantenimiento.setMantenimientoExitoso((Boolean) newValue);
            } else if (newValue instanceof Date) {
                Date fechaFinalizacion = (Date) newValue;
                Date fechaActual = new Date();
                // Validar que la fecha de finalización no sea menor a la fecha actual
                if (fechaFinalizacion.before(fechaActual)) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.WARNING, "La fecha de finalización no puede ser menor a la fecha actual.", ButtonType.OK).showAndWait();
                    });
                    cancelEdit();
                    return; // Salir sin confirmar la edición
                }

                mantenimiento.setFechaFinalizacion((Date) newValue);
            } else if (newValue instanceof Long) {
                mantenimiento.setIdVehiculo((Long) newValue);
            }

            try {
                MantenimientoManagerFactory.get().edit_XML(mantenimiento, String.valueOf(mantenimiento.getIdMantenimiento()));
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.INFORMATION, "Mantenimiento actualizado correctamente.", ButtonType.OK).showAndWait();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Error al actualizar el mantenimiento en el servidor.", ButtonType.OK).showAndWait();
                });
            }
        }

        super.commitEdit((T) newValue);
    }

    private List<Long> obtenerIdVehiculosDisponibles() {
        List<Long> idVehiculos = new ArrayList<>();

        try {
            List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
            });

            for (Vehiculo vehiculo : vehiculos) {
                idVehiculos.add(vehiculo.getIdVehiculo());
            }

            System.out.println("Vehículos disponibles: " + idVehiculos);
        } catch (Exception e) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "Error al cargar vehículos disponibles.", ButtonType.OK).showAndWait();
            });
        }
        return idVehiculos;
    }
}
