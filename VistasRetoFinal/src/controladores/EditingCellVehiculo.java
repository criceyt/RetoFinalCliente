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
/**
 * Clase que representa una celda editable personalizada para la clase
 * {@link Vehiculo}. Esta clase permite la edición de celdas con diferentes
 * tipos de controles gráficos, como {@link TextField}, {@link DatePicker} y
 * {@link ChoiceBox}, dependiendo del tipo de dato de la celda.
 *
 * @param <T> El tipo de dato de la celda (String, Date, Enum, Integer).
 */
public class EditingCellVehiculo<T> extends TableCell<Vehiculo, T> {

    // Atributos para los controles de edición: TextField, DatePicker, ChoiceBox
    private TextField textField;
    private DatePicker datePicker;
    private ChoiceBox<TipoVehiculo> choiceBox;

    /**
     * Constructor por defecto de la clase {@link EditingCellVehiculo}.
     */
    public EditingCellVehiculo() {
    }

    /**
     * Método que se ejecuta para comenzar la edición de la celda. Dependiendo
     * del tipo de dato de la celda, se crea y muestra el control de edición
     * correspondiente (TextField, DatePicker, ChoiceBox, o TextField para
     * enteros).
     */
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
                setGraphic(textField); // Muestra el TextField para enteros
            } else {
                setText(getString());
                setGraphic(null); // Solo muestra el texto sin controles gráficos
                setContentDisplay(ContentDisplay.TEXT_ONLY); // Muestra solo el texto
            }
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Solo el gráfico (control de edición)
        }
    }

    /**
     * Método que cancela la edición de la celda, restaurando el valor original
     * del texto y eliminando el control gráfico de edición.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString()); // Muestra el texto de la celda original
        setGraphic(null); // Elimina el control gráfico
        setContentDisplay(ContentDisplay.TEXT_ONLY); // Solo muestra el texto
    }

    /**
     * Método que actualiza el contenido de la celda cuando se cambia el valor.
     * Si la celda está en modo de edición, muestra el control gráfico
     * correspondiente (TextField, DatePicker, ChoiceBox), de lo contrario,
     * muestra el texto de la celda.
     *
     * @param item El nuevo valor de la celda.
     * @param empty Indica si la celda está vacía.
     */
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

    /**
     * Método para crear el {@link DatePicker} y habilitar su edición. Este
     * método inicializa el DatePicker con el valor actual de la celda, lo
     * convierte en una instancia {@link LocalDate} y establece la acción para
     * confirmar la edición al seleccionar una fecha. Además, permite cancelar
     * la edición si se presiona la tecla ESC.
     */
    private void createDatePicker() {
        datePicker = new DatePicker();
        // Establece el valor inicial del DatePicker basado en el valor de la celda
        datePicker.setValue(((Date) getItem()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        // Establece la acción que confirma la edición al seleccionar una fecha
        datePicker.setOnAction(event -> commitEdit((T) Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        // Si se presiona ESC, cancela la edición
        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit(); // Cancela la edición al presionar ESC
            }
        });
    }

    /**
     * Método para crear un {@link TextField} y habilitar su edición. Este
     * método inicializa un TextField con el valor actual de la celda, ajusta su
     * tamaño y establece las acciones para confirmar la edición al presionar
     * Enter o cuando el foco se pierde. Además, permite cancelar la edición si
     * se presiona la tecla ESC.
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2); // Ajusta el tamaño del TextField

        // Bandera para controlar si se canceló la edición al presionar ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        // Al presionar Enter, se confirma la edición
        textField.setOnAction(event -> commitEdit((T) textField.getText()));

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

    /**
     * Método para crear un {@link TextField} y habilitar su edición para
     * valores de tipo {@link Integer}. Este método inicializa un TextField con
     * el valor actual de la celda y ajusta su tamaño. Cuando el usuario
     * presiona Enter, se intenta convertir el texto en un número entero y, si
     * es válido, se confirma la edición. Si el texto no es un número entero
     * válido, se muestra una alerta. Además, permite cancelar la edición al
     * presionar la tecla ESC.
     */
    private void createTextFieldInteger() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2); // Ajusta el tamaño del TextField

        // Bandera para controlar si se canceló la edición al presionar ESC
        final BooleanProperty presionarEsc = new SimpleBooleanProperty(false);

        // Al presionar Enter, se intenta convertir el texto a Integer y confirmar la edición si es válido
        textField.setOnAction(event -> {
            try {
                Integer newValue = Integer.parseInt(textField.getText());
                commitEdit((T) newValue); // Al presionar enter se confirma la edición
            } catch (NumberFormatException e) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.WARNING, "Por favor, ingrese un número entero válido.", ButtonType.OK).showAndWait();
                });
            }
        });

        // Detecta cuando el foco se pierde para confirmar la edición si el texto es válido
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

    /**
     * Método para crear un {@link ChoiceBox} y habilitar su edición para
     * valores de tipo {@link TipoVehiculo}. Este método inicializa un ChoiceBox
     * con los valores del enum {@link TipoVehiculo}, lo llena con las opciones
     * disponibles y permite confirmar la edición cuando el usuario selecciona
     * una opción. También permite cancelar la edición al presionar la tecla
     * ESC.
     */
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

            // Si se presiona ESC, cancela la edición
            choiceBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();  // Si presionamos ESC, cancela la edición
                }
            });
        }
    }

    /**
     * Método para obtener el valor de la celda como una cadena de texto. Si el
     * valor de la celda es nulo, se devuelve una cadena vacía. De lo contrario,
     * se devuelve el valor de la celda convertido a cadena.
     *
     * @return El valor de la celda como una cadena de texto.
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    /**
     * Método para confirmar la edición de un valor en una celda de la tabla.
     * Dependiendo del tipo de valor editado, se realiza la actualización
     * correspondiente en el objeto {@link Vehiculo}. El valor puede ser de tipo
     * String, Integer, Date o Enum. Si se edita un valor de tipo {@link Date},
     * se valida que no sea una fecha futura. Después de modificar el vehículo,
     * intenta guardar los cambios en el servidor.
     *
     * @param newValue El nuevo valor que se ha editado en la celda.
     */
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
