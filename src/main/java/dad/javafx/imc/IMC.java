package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.layout.HBox;

public class IMC extends Application {

	private Label pesoLabel, kilosLabel, alturaLabel, centimetrosLabel, imcLabel, estadoCorporalLabel;
	private TextField pesoText, alturaText;
	
	private DoubleProperty peso = new SimpleDoubleProperty(0);
	private DoubleProperty altura = new SimpleDoubleProperty(0);
	private DoubleProperty imc = new SimpleDoubleProperty(0);
	private StringProperty estadoCorporal = new SimpleStringProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		pesoLabel = new Label("Peso: ");
		kilosLabel = new Label(" Kg");
		alturaLabel = new Label("Altura: ");
		centimetrosLabel = new Label(" cm");
		imcLabel = new Label();
		estadoCorporalLabel = new Label();
		
		pesoText = new TextField();
		pesoText.setPrefColumnCount(4);
		
		alturaText = new TextField();
		alturaText.setPrefColumnCount(3);
		
		HBox pesobox = new HBox();
		pesobox.setAlignment(Pos.BASELINE_CENTER);
		pesobox.getChildren().addAll(pesoLabel, pesoText, kilosLabel);
		
		HBox alturabox = new HBox();
		alturabox.setAlignment(Pos.BASELINE_CENTER);
		alturabox.getChildren().addAll(alturaLabel, alturaText, centimetrosLabel);
		
		pesoText.textProperty().bindBidirectional(peso, new NumberStringConverter());
		alturaText.textProperty().bindBidirectional(altura, new NumberStringConverter());
		
		imc.bind(peso.multiply((altura.multiply(100)).multiply(altura.multiply(100))));
		
		imcLabel.textProperty().bind(imc.asString().concat(" imc"));
		
		estadoCorporalLabel.textProperty().bind(
				Bindings
					.when(estadoCorporal.isEmpty())
					.then("No hay IMC que calcular")
			);
	}

}
