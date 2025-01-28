/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import logica.VehiculoManagerFactory;
import modelo.TipoVehiculo;
import modelo.Vehiculo;

/**
 *
 * @author urkiz
 */
public class EditingCellVehiculo<T> extends TableCell<Vehiculo, T> {

    // Atributos para los controles de edición: TextField, DatePicker, ChoiceBox
    private TextField textField;
    private DatePicker datePicker;
    private ChoiceBox<TipoVehiculo> choiceBox;

    // Constructor por defecto
    public EditingCellVehiculo() {
    }

    // Método para comenzar la edición de la celda
    @Override
    public void startEdit() {
        if (!isEmpty()) { // Asegura que no esté vacío
            super.startEdit();
            T item = getItem();

            // Dependiendo del tipo de dato, se crea el control adecuado
            if (item instanceof String) {
                createTextField();
                textField.setText(getString());
                setGraphic(textField); // Muestra el TextField
            } else if (item instanceof Date) {
                createDatePicker();
                datePicker.setValue(((Date) item).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                setGraphic(datePicker); // Muestra el DatePicker
            } else if (item instanceof Enum) {
                createChoiceBox();
                choiceBox.setValue((TipoVehiculo) item);
                setGraphic(choiceBox); // Muestra el ChoiceBox
            } else if (item instanceof Integer) {
                createTextFieldInteger();
                textField.setText(getString());
                setGraphic(textField); // Muestra el TextField
            } else {
                setText(getString());
                setGraphic(null); // Solo muestra el texto sin controles gráficos
                setContentDisplay(ContentDisplay.TEXT_ONLY); // Muestra solo el texto
            }
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Solo el gráfico (control de edición)
        }
    }

    // Método para cancelar la edición
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString()); // Muestra el texto de la celda original
        setGraphic(null); // Elimina el control gráfico
        setContentDisplay(ContentDisplay.TEXT_ONLY); // Solo muestra el texto
    }

    // Método para actualizar el contenido de la celda cuando se cambia el valor
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null); // Si está vacío, no muestra texto ni controles
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (item instanceof String) {
                    textField.setText(getString());
                    setGraphic(textField); // Si se está editando, muestra el TextField
                } else if (item instanceof Date) {
                    datePicker.setValue(((Date) item).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    setGraphic(datePicker); // Si es una fecha, muestra el DatePicker
                } else if (item instanceof Enum) {
                    createChoiceBox();
                    choiceBox.setValue((TipoVehiculo) item);
                    setGraphic(choiceBox); // Si es un enum, muestra el ChoiceBox
                } else {
                    setText(getString());
                    setGraphic(null); // Muestra solo el texto en caso de no ser un tipo editable
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            } else {
                setText(getString()); // Si no está editando, muestra el texto
                setGraphic(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY); // Solo texto
            }
        }
    }

    // Método para crear el DatePicker y habilitar su edición
    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setValue(((Date) getItem()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        datePicker.setOnAction(event -> commitEdit((T) Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))); // Al seleccionar fecha, se confirma la edición
        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit(); // Si presionamos ESC, cancela la edición
            }
        });
    }

    // Método para crear un TextField y habilitar su edición
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2); // Ajusta el tamaño del TextField

        // Bandera para controlar si se canceló la edición al presionar ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        textField.setOnAction(event -> commitEdit((T) textField.getText())); // Al presionar enter se confirma la edición

        // Detecta cuando el foco se pierde para confirmar la edición
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
            if (!arg2 && !presionarEsc.get()) {
                commitEdit((T) textField.getText());
            }
        });

        // Si se presiona ESC, cancela la edición
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(true); // Marca la bandera de ESC presionado
                cancelEdit();
            }
        });

        // Restablece la bandera cuando se suelta la tecla ESC
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(false); // Restablece la bandera
            }
        });
    }

    // Método para crear un TextField y habilitar su edición (Integer)
    private void createTextFieldInteger() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2); // Ajusta el tamaño del TextField

        // Bandera para controlar si se canceló la edición al presionar ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        textField.setOnAction(event -> {
            // Intentar convertir el texto a Integer, si no es posible mostrar la alerta
            try {
                Integer newValue = Integer.parseInt(textField.getText());
                commitEdit((T) newValue); // Al presionar enter se confirma la edición
            } catch (NumberFormatException e) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.WARNING, "Por favor, ingrese un número entero válido.", ButtonType.OK).showAndWait();
                });
            }
        }); // Al presionar enter se confirma la edición

        // Detecta cuando el foco se pierde para confirmar la edición
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
            if (!arg2 && !presionarEsc.get()) {
                try {
                    Integer newValue = Integer.parseInt(textField.getText());
                    commitEdit((T) newValue);
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.WARNING, "Por favor, ingrese un número entero válido.", ButtonType.OK).showAndWait();
                    });
                }
            }
        });

        // Si se presiona ESC, cancela la edición
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(true); // Marca la bandera de ESC presionado
                cancelEdit();
            }
        });

        // Restablece la bandera cuando se suelta la tecla ESC
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(false); // Restablece la bandera
            }
        });
    }

    // Método para crear el ChoiceBox y habilitar su edición
    private void createChoiceBox() {
        choiceBox = new ChoiceBox<>();
        choiceBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2); // Ajusta el tamaño del ChoiceBox

        // Obtener los valores del enum TipoVehiculo
        List<TipoVehiculo> tipoVehiculoEnum = Arrays.asList(TipoVehiculo.values()); // Usa el enum directamente, no los ordinales

        // Si no hay valores para cargar, muestra una alerta (aunque aquí siempre debería haber valores)
        if (tipoVehiculoEnum.isEmpty()) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.WARNING, "No hay TipoVehiculo disponibles", ButtonType.OK).showAndWait();
            });
        } else {
            // Cargar las opciones de tipo de vehículo en el ChoiceBox
            choiceBox.setItems(FXCollections.observableArrayList(tipoVehiculoEnum));

            choiceBox.setOnAction(event -> {
                TipoVehiculo selectedOption = choiceBox.getValue();  // Obtén el valor seleccionado
                if (selectedOption != null && !selectedOption.equals(getItem())) {  // Si el valor seleccionado es diferente
                    commitEdit((T) selectedOption);  // Confirma la edición
                } else {
                    cancelEdit();  // Si no cambia, cancela la edición
                }
            });

            choiceBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();  // Si presionamos ESC, cancela la edición
                }
            });
        }
    }

    // Método para obtener el valor de la celda como cadena de texto
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    @Override
    public void commitEdit(Object newValue) {

        // Verificar si la fila está vacía o nula
        Vehiculo vehiculo = (Vehiculo) getTableRow().getItem();
        if (vehiculo == null) {
            System.out.println("No hay un vehículo seleccionado en esta fila.");
            return;  // Sale si no hay datos en la fila
        }

        // Imprimir la columna que se está editando
        System.out.println("Columna editada: " + getTableColumn().getText());

        // Verificar el tipo de nuevo valor
        System.out.println("Nuevo valor editado: " + newValue);

        if (newValue instanceof String) {
            // Caso cuando el valor es String (para columnas como 'Color', 'Marca', etc.)
            System.out.println("Editando un valor String");

            if (getTableColumn().getText().equals("Color")) {
                vehiculo.setColor((String) newValue);
            } else if (getTableColumn().getText().equals("Marca")) {
                vehiculo.setMarca((String) newValue);
            } else if (getTableColumn().getText().equals("Modelo")) {
                vehiculo.setModelo((String) newValue);
            }
            // ... Aquí más columnas que contengan String

        } else if (newValue instanceof Integer) {
            // Caso cuando el valor es Integer (para columnas como 'Km', 'Precio', 'Potencia')
            System.out.println("Editando un valor Integer");

            if (getTableColumn().getText().equals("Kilometro")) {
                vehiculo.setKm((Integer) newValue);
            } else if (getTableColumn().getText().equals("Precio")) {
                vehiculo.setPrecio((Integer) newValue);
            } else if (getTableColumn().getText().equals("Potencia")) {
                vehiculo.setPotencia((Integer) newValue);
            }
        } else if (newValue instanceof Date) {
            Date fechaAlta = (Date) newValue;
            Date fechaActual = new Date();
            // Validación: la fecha de finalización no puede ser menor a la fecha actual
            if (fechaAlta.after(fechaActual)) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.WARNING, "La fecha de Alta no puede ser en el FUTURO", ButtonType.OK).showAndWait();
                });
                cancelEdit();
                return; // Cancela la edición si la fecha es inválida
            }
            vehiculo.setFechaAlta((Date) newValue);
        } else if (newValue instanceof Enum) {
            vehiculo.setTipoVehiculo((TipoVehiculo) (Enum) newValue);
        }

        // Después de modificar el vehículo, intenta guardar los cambios
        try {
            String idParseado = String.valueOf(vehiculo.getIdVehiculo());
            VehiculoManagerFactory.get().edit_XML(vehiculo, idParseado);
        } catch (Exception e) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "Error al actualizar el vehículo en el servidor.", ButtonType.OK).showAndWait();
            });
        }

        super.commitEdit((T) newValue); // Finaliza la edición
    }
}
