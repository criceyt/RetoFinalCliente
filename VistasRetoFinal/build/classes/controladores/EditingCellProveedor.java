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
import java.util.stream.Collectors;
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
import logica.ProveedorManagerFactory;
import modelo.Proveedor;
import modelo.TipoVehiculo;

/**
 * Clase personalizada para la edición de celdas en la tabla de Proveedores.
 * Permite editar celdas que contienen diferentes tipos de datos como String,
 * Date, Long (enum).
 *
 * @author 2dam
 */
public class EditingCellProveedor<T> extends TableCell<Proveedor, T> {

    // Atributos para los controles de edición: TextField, DatePicker, ChoiceBox
    private TextField textField;
    private DatePicker datePicker;
    private ChoiceBox<TipoVehiculo> choiceBox;

    // Constructor por defecto
    public EditingCellProveedor() {
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

    // Método para confirmar la edición de los valores
    @Override
    public void commitEdit(Object newValue) {

        // Se crea un porveedor
        Proveedor proveedores = (Proveedor) getTableRow().getItem();
        if (proveedores != null) {

            // Validaciones para los valores que sean String
            if (newValue instanceof String) {
                // Validación: la nombre no puede ser mayor a 50 caracteres
                if (((String) newValue).length() > 50) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.WARNING, "El Nombre ni Especialidad no puede tener más de 50 caracteres.", ButtonType.OK).showAndWait();
                    });
                    cancelEdit();
                    return; // Cancela la edición si la descripción es inválida
                }
                System.out.println(getTableColumn().getText());
                // Si la columna que se está editando es 'Nombre', se actualiza el nombre
                if (getTableColumn().getText().equals("Nombre")) {
                    proveedores.setNombreProveedor((String) newValue);
                } // Si la columna que se está editando es 'Especialidad', se actualiza la especialidad
                else if (getTableColumn().getText().equals("Especialidad")) {
                    proveedores.setEspecialidad((String) newValue);
                }

                // Validaciones para las Fechas
            } else if (newValue instanceof Date) {
                Date fechaFinalizacion = (Date) newValue;
                Date fechaActual = new Date();
                // Validación: la fecha de finalización no puede ser menor a la fecha actual
                if (fechaFinalizacion.after(fechaActual)) {
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.WARNING, "La ultima Actividad no puede ser en el FUTURO", ButtonType.OK).showAndWait();
                    });
                    cancelEdit();
                    return; // Cancela la edición si la fecha es inválida
                }
                proveedores.setUltimaActividad((Date) newValue);

            } else if (newValue instanceof Enum) {
                proveedores.setTipoVehiculo((TipoVehiculo) (Enum) newValue);
            }

            try {

                String idParseado = String.valueOf(proveedores.getIdProveedor());
                ProveedorManagerFactory.get().edit_XML(proveedores, idParseado);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Error al actualizar el proveedor en el servidor.", ButtonType.OK).showAndWait();
                });
            }
        }

        super.commitEdit((T) newValue); // Finaliza la edición
    }
}
