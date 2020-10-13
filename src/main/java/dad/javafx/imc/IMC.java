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
	
	private DoubleProperty pesoProperty = new SimpleDoubleProperty(0);
	private DoubleProperty alturaProperty = new SimpleDoubleProperty(0);
	private DoubleProperty imcProperty = new SimpleDoubleProperty(0);
	private StringProperty simcProperty = new SimpleStringProperty(this, "imc");
	private StringProperty estadoCorporalProperty = new SimpleStringProperty(this, "estado");

	private void calcularIMC() {
		if ((pesoProperty.get() == 0) || (alturaProperty.get() == 0))
			imcProperty.set(0);
		else {
			double peso, altura, resultado;
			peso = pesoProperty.get();
			altura = alturaProperty.get();
			resultado = (peso / (altura * altura));
			imcProperty.set(Math.round(resultado * 100.00) / 100.00);
		}
	}

	public void obtenerEstado() {
		
		if (imcProperty.get() == 0) {
			simcProperty.set("IMC: (peso * altura ^ 2)");
			estadoCorporalProperty.set("Bajo peso / Normal / Sobrepeso / Obeso");
		} else {
			simcProperty.set("IMC: " + imcProperty.get());
			if (imcProperty.get() < 18.5)
				estadoCorporalProperty.set("Bajo Peso");
			else if (imcProperty.get() >= 18.5 && imcProperty.get() < 25)
				estadoCorporalProperty.set("Normal");
			else if (imcProperty.get() >= 25 && imcProperty.get() < 30)
				estadoCorporalProperty.set("Sobrepeso");
			else
				estadoCorporalProperty.set("Obeso");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		obtenerEstado();
		
		Label pesoLabel = new Label("Peso: ");
		Label kilosLabel = new Label(" Kg");
		Label alturaLabel = new Label("Altura: ");
		Label centimetrosLabel = new Label(" m");
		Label imcLabel = new Label("IMC: (peso * altura ^ 2");
		Label estadoCorporalLabel = new Label("Bajo peso / Normal / Sobrepeso / Obeso");

		TextField pesoText = new TextField();
		pesoText.setPrefColumnCount(3);
		pesoText.setMaxWidth(100);
		pesoText.setAlignment(Pos.CENTER);
		
		TextField alturaText = new TextField();
		alturaText.setPrefColumnCount(3);
		alturaText.setMaxWidth(100);
		alturaText.setAlignment(Pos.CENTER);

		pesoText.textProperty().bindBidirectional(pesoProperty, new NumberStringConverter());
		pesoProperty.addListener((o, ov, nv) -> calcularIMC());

		alturaText.textProperty().bindBidirectional(alturaProperty, new NumberStringConverter());
		alturaProperty.addListener((o, ov, nv) -> calcularIMC());

		imcLabel.textProperty().bindBidirectional(simcProperty);
		estadoCorporalLabel.textProperty().bindBidirectional(estadoCorporalProperty);
		imcProperty.addListener((o, ov, nv) -> obtenerEstado());

		HBox pesobox = new HBox();
		pesobox.setAlignment(Pos.BASELINE_CENTER);
		pesobox.getChildren().addAll(pesoLabel, pesoText, kilosLabel);

		HBox alturabox = new HBox();
		alturabox.setAlignment(Pos.BASELINE_CENTER);
		alturabox.getChildren().addAll(alturaLabel, alturaText, centimetrosLabel);

		HBox imcbox = new HBox();
		imcbox.setAlignment(Pos.BASELINE_CENTER);
		imcbox.getChildren().addAll(imcLabel);
		imcbox.setSpacing(5);

		HBox estadoCorporalbox = new HBox();
		estadoCorporalbox.setAlignment(Pos.BASELINE_CENTER);
		estadoCorporalbox.getChildren().addAll(estadoCorporalLabel);
		estadoCorporalbox.setSpacing(5);

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
