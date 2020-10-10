package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IMC extends Application {

	private Label pesoLabel, kilosLabel, alturaLabel, centimetrosLabel, imcLabel, estadoCorporalLabel;
	private TextField pesoText, alturaText;

	private DoubleProperty pesoProperty = new SimpleDoubleProperty(0);
	private DoubleProperty alturaProperty = new SimpleDoubleProperty(0);
	private DoubleProperty imcProperty = new SimpleDoubleProperty(0);
	private StringProperty estadoCorporalProperty = new SimpleStringProperty();

	public void cambiarEstadoCorporal() {
		if (imcProperty.get() < 18.5)
			estadoCorporalProperty.set("Bajo Peso");
		else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25)
			estadoCorporalProperty.set("Normal");
		else if (imcProperty.get() >= 25 && imcProperty.get() < 30)
			estadoCorporalProperty.set("Sobrepeso");
		else
			estadoCorporalProperty.set("Obeso");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoLabel = new Label("Peso: ");
		kilosLabel = new Label(" Kg");
		alturaLabel = new Label("Altura: ");
		centimetrosLabel = new Label(" cm");

		pesoText = new TextField();
		pesoText.setPrefColumnCount(3);

		alturaText = new TextField();
		alturaText.setPrefColumnCount(3);

		HBox pesobox = new HBox();
		pesobox.setAlignment(Pos.BASELINE_CENTER);
		pesobox.getChildren().addAll(pesoLabel, pesoText, kilosLabel);

		HBox alturabox = new HBox();
		alturabox.setAlignment(Pos.BASELINE_CENTER);
		alturabox.getChildren().addAll(alturaLabel, alturaText, centimetrosLabel);

		pesoText.textProperty().bindBidirectional(pesoProperty, new NumberStringConverter());
		alturaText.textProperty().bindBidirectional(alturaProperty, new NumberStringConverter());

		// Antes de aqui esta bien

		imcLabel = new Label("IMC : (peso * altura ^ 2");
		estadoCorporalLabel = new Label("Bajo peso / Normal / Sobrepeso / Obeso");

		imcProperty.bind((pesoProperty.divide((alturaProperty.multiply(alturaProperty)))));

		imcLabel.textProperty().bind(imcProperty.asString());

		HBox imcbox = new HBox();
		imcbox.setAlignment(Pos.BASELINE_CENTER);
		imcbox.getChildren().addAll(imcLabel);
		imcbox.setSpacing(5);

		estadoCorporalLabel.textProperty().bind(estadoCorporalProperty);
		pesoProperty.addListener((o, ov, nv) -> cambiarEstadoCorporal());
		alturaProperty.addListener((o, ov, nv) -> cambiarEstadoCorporal());

		HBox estadoCorporalbox = new HBox();
		estadoCorporalbox.setAlignment(Pos.BASELINE_CENTER);
		estadoCorporalbox.getChildren().addAll(estadoCorporalLabel);
		estadoCorporalbox.setSpacing(5);

		// A partir de aqui esta bien

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);
		root.getChildren().addAll(pesobox, alturabox, imcbox, estadoCorporalbox);

		Scene escena = new Scene(root, 320, 200);

		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC");
		primaryStage.show();
	}

	/**
	 * Método encargado de iniciar la aplicación.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
