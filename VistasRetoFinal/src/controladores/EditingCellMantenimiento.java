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

    /**
     * Inicia la edición de la celda si no está vacía. Dependiendo del tipo de
     * dato contenido en la celda, se crea el editor correspondiente:
     * <ul>
     * <li>Si el contenido es un {@code String}, se usa un
     * {@link TextField}.</li>
     * <li>Si el contenido es un {@code Boolean}, se usa un
     * {@link CheckBox}.</li>
     * <li>Si el contenido es un {@code Date}, se usa un
     * {@link DatePicker}.</li>
     * <li>Si el contenido es un {@code Long}, y el ID del vehículo en
     * {@link Mantenimiento} es 0, se usa un {@link ChoiceBox} con los vehículos
     * disponibles.</li>
     * <li>Si el tipo de dato no es compatible, la edición no se inicia.</li>
     * </ul>
     *
     * Si la celda contiene un tipo no soportado, se mantiene el texto actual y
     * no se habilita edición.
     */
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

    /**
     * Cancela la edición de la celda y restaura su estado original. Se muestra
     * el valor actual del elemento sin ninguna interfaz gráfica de edición.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    /**
     * Actualiza el contenido de la celda cuando cambia su valor o su estado de
     * edición.
     * <p>
     * Si la celda está vacía, se limpia su contenido. Si está en modo de
     * edición, se muestra el editor correspondiente según el tipo de dato. Si
     * no está en edición, simplemente muestra el valor como texto.
     * </p>
     *
     * @param item El nuevo valor del elemento en la celda.
     * @param empty Indica si la celda está vacía.
     */
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

    /**
     * Crea un campo de texto (TextField) para la edición de la celda.
     * <p>
     * - Establece el valor inicial con el contenido actual de la celda. -
     * Configura la lógica para confirmar la edición cuando se presiona Enter o
     * cuando se pierde el foco, siempre que no se haya cancelado con la tecla
     * ESC. - Maneja la tecla ESC para cancelar la edición y evitar cambios no
     * deseados.
     * </p>
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // Bandera para controlar si se canceló por ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        // Confirma la edición cuando se presiona Enter
        textField.setOnAction(event -> commitEdit((T) textField.getText()));

        // Confirma la edición al perder el foco, salvo que se haya cancelado con ESC
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
            if (!arg2 && !presionarEsc.get()) {
                commitEdit((T) textField.getText());
            }
        });

        // Marca la edición como cancelada si se presiona ESC
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(true); // Marca la bandera
                cancelEdit();
            }
        });

        // Restablece la bandera cuando se libera la tecla ESC
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                presionarEsc.set(false); // Restablece la bandera
            }
        });
    }

    /**
     * Crea un componente CheckBox y lo configura con el valor actual del item.
     * También establece el comportamiento de acción al seleccionar el CheckBox
     * y el evento de tecla ESCAPE para cancelar la edición.
     */
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

    /**
     * Crea un componente DatePicker y lo configura con el valor actual del
     * item, transformándolo de Date a LocalDate. Establece el comportamiento de
     * acción al seleccionar una fecha y el evento de tecla ESCAPE para cancelar
     * la edición.
     */
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

    /**
     * Crea un componente ChoiceBox y lo configura con el ancho adecuado. Luego
     * obtiene los vehículos disponibles para ser mostrados en el ChoiceBox. Si
     * no hay vehículos disponibles, muestra una alerta de advertencia.
     */
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
        /**
         * Configura el ChoiceBox con los vehículos disponibles. Al seleccionar
         * un vehículo, se verifica si el valor seleccionado es diferente al
         * valor actual. Si ha cambiado, se confirma la edición; de lo
         * contrario, se cancela. También se configura la acción para la tecla
         * ESCAPE para cancelar la edición.
         */
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

    /**
     * Obtiene el valor del item como un String. Si el item es nulo, devuelve
     * una cadena vacía.
     *
     * @return El valor del item como String o una cadena vacía si el item es
     * nulo.
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    /**
     * Confirma la edición del item con el nuevo valor proporcionado. Si el
     * valor es una descripción (String), valida que no tenga más de 50
     * caracteres. Si el valor es una fecha, valida que la fecha de finalización
     * no sea anterior a la fecha actual. Dependiendo del tipo de valor, se
     * actualiza el objeto `Mantenimiento` y se realiza la actualización en el
     * servidor. Si hay algún error, se muestra un mensaje de advertencia o
     * error.
     *
     * @param newValue El nuevo valor para el item a editar.
     */
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

    /**
     * Obtiene una lista de identificadores de vehículos disponibles. Realiza
     * una solicitud al servidor para obtener todos los vehículos, luego extrae
     * los identificadores de cada uno y los almacena en una lista. Si ocurre un
     * error durante la carga de los vehículos, muestra un mensaje de error en
     * la interfaz de usuario.
     *
     * @return Una lista de identificadores de vehículos disponibles.
     */
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
