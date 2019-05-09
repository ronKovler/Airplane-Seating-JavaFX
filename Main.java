import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.application.*;
import java.time.LocalDate;
import java.util.function.UnaryOperator; 
//import java.util.Array;

/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main extends Application implements EventHandler<ActionEvent> 
{
    Stage window;
    String[] fromList = {"San Francisco, CA"};
    String[] toList = {"New York City, NY", "Saltlake City, UT", "Miami, FL"};
    String firstName, lastName, fromCity, toCity, foodOptionChosen;
    
    ComboBox fromMenu, toMenu;
    LocalDate selectedDate;
    DatePicker flightDate;
    
    Label errorMessageLabel1 = new Label("");
    Label errorMessageLabel2 = new Label("");
    Label errorMessageLabel3 = new Label("");
    
    Airplane a1 = new Airplane(4, 20, 3, 4, 870);
    Airplane a2 = new Airplane(4, 15, 2, 3, 300);
    Airplane a3 = new Airplane(6, 35, 6, 8, 950);
    Airplane curPlane;
    AirplaneSeat selectedSeat, infoSeat;

    Image plane = new Image("Layout2.png");
    ImageView backgroundPlane = new ImageView(plane);
    Image emptySeat, occupiedSeat;
    ImageView emptyView, occupiedView;
     
    //textfields
    TextField seatPrice,seatCategory,seatPosition;
    TextField lastNm, firstNm;
    
    Scene scene1, scene2, scene3, scene4;
    //Buttons
    Button fromInfoToSeat, confirmSeatButton;
    Button[][] seats;
    RadioButton vegan, vegetarian, standard, kosher;
    Button confirmPay, backToSelect;
    //
    
    BorderPane layoutWindow2Main;
    public static void main(String[] args){
        launch(args);
        //Main JavaFX code goes inside start VVV
        
    }
    //Start of the program, Begin opening scenes ect
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage; 
        window.setTitle("Test Program Seating");
        //All layouts
        VBox layoutWindow1Left = new VBox(29);
        VBox layoutWindow1Right = new VBox(20);
        HBox layoutWindow1Main = new HBox(20);
        
          
        //Items in layouts
        Label firstNmLabel = new Label("First Name: ");
        Label lastNmLabel = new Label("Last Name: ");
        firstNm = new TextField();
        lastNm = new TextField();
        Label fromLabel = new Label("From: ");
        Label toLabel = new Label("To: ");
        Label flightDateLabel = new Label("Flight Date");
        
        fromInfoToSeat = new Button("Select Seating");
        fromInfoToSeat.setOnAction(this);
                
        
        fromMenu = new ComboBox(FXCollections.observableArrayList(fromList));
        toMenu = new ComboBox(FXCollections.observableArrayList(toList));
        
        flightDate = new DatePicker();
        
        
           
        layoutWindow1Left.getChildren().addAll(firstNmLabel, lastNmLabel, fromLabel, toLabel, flightDateLabel);
        layoutWindow1Right.getChildren().addAll(firstNm, lastNm, fromMenu, toMenu, flightDate, errorMessageLabel1);
        layoutWindow1Main.getChildren().addAll(layoutWindow1Left, layoutWindow1Right, fromInfoToSeat); // EDITED HEREEEEEEEEEE
        layoutWindow1Main.setAlignment(Pos.CENTER);
        layoutWindow1Main.setPadding(new Insets(50,10,10,10));
         
        scene1 = new Scene(layoutWindow1Main, 1280, 720);
        window.setScene(scene1);
        window.show();
        buildScene2();
    }
    
    public void buildScene2(){
        layoutWindow2Main = new BorderPane();
        VBox clickedItemInformation = new VBox(7);
        
        seatPrice = new TextField("Price   ");
        seatPrice.setEditable(false);
        seatCategory = new TextField("Category");
        seatCategory.setEditable(false);
        seatPosition = new TextField("Position");
        seatPosition.setEditable(false);
        
        Label seatPriceLabel = new Label("Price:");
        Label seatCategoryLabel = new Label("Category:");
        Label seatPositionLabel = new Label("Position:");
        Label foodOptionLabel = new Label("Food Options:");
        
        clickedItemInformation.getChildren().addAll(seatPriceLabel, seatPrice, seatCategoryLabel, seatCategory, seatPositionLabel, seatPosition);
        clickedItemInformation.setAlignment(Pos.CENTER_LEFT);
        clickedItemInformation.setPadding(new Insets(10, 10, 10, 30));
        layoutWindow2Main.setLeft(clickedItemInformation);
        
        //Radiobuttons for food options
        ToggleGroup main = new ToggleGroup();
        vegan = new RadioButton("Vegan");
        vegetarian = new RadioButton("Vegetarian");
        standard = new RadioButton("Standard");
        kosher = new RadioButton("Kosher");
        vegan.setToggleGroup(main);
        vegetarian.setToggleGroup(main);
        standard.setToggleGroup(main);
        kosher.setToggleGroup(main);
        
        VBox foodLayout = new VBox(10);
        foodLayout.getChildren().addAll(foodOptionLabel, vegan, vegetarian, standard, kosher);
        foodLayout.setAlignment(Pos.CENTER_LEFT);
        foodLayout.setPadding(new Insets(10, 30, 10, 10));
        layoutWindow2Main.setRight(foodLayout);
        
        confirmSeatButton = new Button("Confirm Options");
        confirmSeatButton.setOnAction(this);
        VBox buttonBottom = new VBox(20);
        
        HBox keyLayout = new HBox(15);
        
        buttonBottom.setPadding(new Insets(20, 20, 20, 20));
        buttonBottom.setAlignment(Pos.CENTER);
        buttonBottom.getChildren().addAll(errorMessageLabel2, confirmSeatButton);
        layoutWindow2Main.setBottom(buttonBottom);
        
        curPlane = a1;
        seats = new Button[curPlane.numRows][curPlane.numSeatsPerRow];
        
        //creating image object for seat display
        emptySeat = new Image(getClass().getResourceAsStream("EmptySeat.png"));
        occupiedSeat = new Image(getClass().getResourceAsStream("OccupiedSeat.png"));
        
        GridPane seatsPaneTop = new GridPane();
        GridPane seatsPaneBot = new GridPane();
        VBox seatsPaneMain = new VBox(30);
        seatsPaneMain.setPadding(new Insets(0, 0, 0, 60));
        //Places empty space inbetween seat rows to simulate leg room
        for(int i = 0; i < curPlane.numRows; i++){
            seatsPaneTop.getColumnConstraints().add(new ColumnConstraints(38));
            seatsPaneBot.getColumnConstraints().add(new ColumnConstraints(38));
        }

        int count = 0;
        for (int i = 0; i < curPlane.numRows; i++){
            for (int j = 0; j < curPlane.numSeatsPerRow; j++) {
                boolean flag = AirplaneSeat.isReserved(curPlane.seatList.get(count));
                if( flag){
                    ImageView occupiedView = new ImageView(occupiedSeat);
                    occupiedView.setFitHeight(16);
                    occupiedView.setFitWidth(16);
                    seats[i][j] = new Button("", occupiedView);
                    //reason it wasnt working earlier is possible ea
                }else{
                    emptyView = new ImageView(emptySeat);
                    emptyView.setFitHeight(16);
                    emptyView.setFitWidth(16);
                    seats[i][j]=new Button("", emptyView);
                }
                seats[i][j].setOnAction(this);
                count++;
                //adds seat to specific gridpane to allow for empty space to represent aisle
                if(j < (curPlane.numSeatsPerRow)/2){
                    seatsPaneTop.add(seats[i][j], i, j, 1, 1);
                }else if(j >= (curPlane.numSeatsPerRow)/2){
                    seatsPaneBot.add(seats[i][j],i,j- (curPlane.numSeatsPerRow)/2, 1, 1);
                }
            }
        }
        seatsPaneTop.setAlignment(Pos.CENTER);
        seatsPaneBot.setAlignment(Pos.CENTER);
        seatsPaneMain.getChildren().addAll(seatsPaneTop, seatsPaneBot);
        seatsPaneMain.setAlignment(Pos.CENTER);
        
        StackPane stackPane = new StackPane(); 
        layoutWindow2Main.setCenter(stackPane);
        
        //Retrieving the observable list of the Stack Pane 
        ObservableList list = stackPane.getChildren(); 
      
        //Adding all the nodes to the pane  
        list.addAll(backgroundPlane, seatsPaneMain);
        
        scene2 = new Scene(layoutWindow2Main, 1280, 720);
    }
    
    public void switchToPayScene(){
        HBox layoutWindow3Main = new HBox(40);
        layoutWindow3Main.setAlignment(Pos.CENTER);
        VBox layoutWindow3Left = new VBox(15);
        HBox layoutWindow3LeftInterrior = new HBox(10);
        VBox interriorLeft = new VBox(29);
        VBox interriorRight = new VBox(20);
        
        /*
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        fieldNport = new TextField();
        fieldNport.setTextFormatter(textFormatter);
        */
        
        Label scene3Info = new Label("Flight Information");
        scene3Info.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scene3Info.setUnderline(true);
        Label fromLab = new Label("From: ");
        Label toLab = new Label("To: ");
        Label categoryLab = new Label("Category: ");
        Label seatLab = new Label("Seat: ");
        Label foodLab = new Label("Food Option: "); 
        Label priceLab = new Label("Price: ");
        
        TextField fromF = new TextField(fromCity);
        TextField toF = new TextField(toCity);
        TextField categoryF = new TextField(AirplaneSeat.getCategory(selectedSeat));
        TextField seatF = new TextField(AirplaneSeat.getName(selectedSeat));
        TextField foodF = new TextField(foodOptionChosen);
        TextField priceF = new TextField("$" + Double.toString(AirplaneSeat.getPrice(selectedSeat)));
        
        fromF.setEditable(false);
        toF.setEditable(false);
        categoryF.setEditable(false);
        seatF.setEditable(false);
        foodF.setEditable(false);
        priceF.setEditable(false);
        
        Label scene3Info2 = new Label("Payment Information");
        scene3Info2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scene3Info2.setUnderline(true);
        Label namePayLab = new Label("Name (as it appears on your card)");
        Label cardNumPayLab = new Label("Card Number (no dashes or spaces)");
        Label expDatePayLab = new Label("Expiration Date");
        Label securityPayLab = new Label("Security Code (cvc)");
        
        
        TextField namePayF = new TextField(firstName + " " + lastName);
        RestrictiveTextField cardNumPayF = new RestrictiveTextField();
        cardNumPayF.setMaxLength(16);
        cardNumPayF.setRestrict("[0-9]");
        cardNumPayF.setMaxWidth(150.0);
        
        RestrictiveTextField securityPayF = new RestrictiveTextField();
        securityPayF.setMaxLength(3);
        securityPayF.setRestrict("[0-9]");
        securityPayF.setMaxWidth(35.0);
        
        HBox dateHolderLayout = new HBox(10);
        String[] monthList = {"January" , "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] yearList = {"2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027"};
        ComboBox month = new ComboBox(FXCollections.observableArrayList(monthList));
        ComboBox year = new ComboBox(FXCollections.observableArrayList(yearList));
        dateHolderLayout.getChildren().addAll(month, year);
        
        VBox layoutWindow3Right = new VBox(13);
        layoutWindow3Right.getChildren().addAll(scene3Info2, namePayLab, namePayF, cardNumPayLab, cardNumPayF, expDatePayLab, dateHolderLayout, securityPayLab, securityPayF/*, confirmPay*/);
        
        
        //layoutWindow3Main.getChildren().addAll(layoutWindow3Left,
        backToSelect = new Button("Go Back");
        confirmPay = new Button("Confirm and Pay");
        backToSelect.setOnAction(e -> window.setScene(scene2));
        confirmPay.setOnAction(e -> 
        {
            try{
                if((namePayF.getText()).length() > 1 && cardNumPayF.getText().length() == 16 && securityPayF.getText().length() == 3 && month.getValue() != null && year.getValue() != null){
                    errorMessageLabel3.setText("");
                    createFinalScene();
                }else{
                    throw new PaymentNotFilledException();
                }
            }catch(PaymentNotFilledException e2){
                errorMessageLabel3.setText("Please fill out ALL payment information");
            }
        }
        );
        
        
        interriorLeft.getChildren().addAll(fromLab, toLab, categoryLab, seatLab, foodLab, priceLab);
        interriorRight.getChildren().addAll(fromF, toF, categoryF, seatF, foodF, priceF);
        layoutWindow3LeftInterrior.getChildren().addAll(interriorLeft, interriorRight);
        layoutWindow3Left.getChildren().addAll(scene3Info, layoutWindow3LeftInterrior/*, backToSelect*/);
        layoutWindow3Left.setAlignment(Pos.CENTER);
        
        layoutWindow3Right.setAlignment(Pos.CENTER_LEFT);
        
        layoutWindow3Main.getChildren().addAll(layoutWindow3Left, layoutWindow3Right);
        layoutWindow3Main.setPadding(new Insets(10, 10, 10, 10));
        
        VBox layoutWindow3OUTER = new VBox(20);
        HBox buttonHolderWindow3 = new HBox(150);
        buttonHolderWindow3.getChildren().addAll(backToSelect, confirmPay);
        buttonHolderWindow3.setAlignment(Pos.CENTER);
        layoutWindow3OUTER.getChildren().addAll(layoutWindow3Main, buttonHolderWindow3, errorMessageLabel3);
        layoutWindow3OUTER.setAlignment(Pos.CENTER);
        
        scene3 = new Scene(layoutWindow3OUTER, 1280, 720);
        window.setScene(scene3);
        window.show();
    }
    
    public void createFinalScene(){
        VBox layoutWindow4Main = new VBox(20);
        
        Label success = new Label("Your seat has been successfully reserved.");
        success.setFont(Font.font(Font.getFontNames().get(0),  FontWeight.EXTRA_BOLD, 20));
        success.setPadding(new Insets(10,10,10,10));
        Button closeApp, returnToBeginning;
        closeApp = new Button("Exit");
        returnToBeginning = new Button("Book Another Seat");
        closeApp.setOnAction(e -> Platform.exit());
        returnToBeginning.setOnAction(e -> 
        {
            window.setScene(scene1);
            
            window.show();
        }
        );
        layoutWindow4Main.getChildren().addAll(success, closeApp, returnToBeginning);
        layoutWindow4Main.setPadding(new Insets(10, 10, 10, 10));
        layoutWindow4Main.setAlignment(Pos.CENTER);
        closeApp.setAlignment( Pos.BOTTOM_CENTER);
        returnToBeginning.setAlignment(Pos.BOTTOM_CENTER);
        scene4 = new Scene(layoutWindow4Main, 480, 720);
        window.setScene(scene4);
        window.show();
    }
    
    public void handle(ActionEvent event) 
    {
        if(event.getSource() == fromInfoToSeat){
            try {
                firstName = firstNm.getText();
                lastName = lastNm.getText();
                fromCity = fromMenu.getValue().toString();
                toCity = toMenu.getValue().toString();
                selectedDate = flightDate.getValue();
                Label flightInfo = new Label("Available seating for " + lastName + ", " + firstName + "--from " + fromCity + " to " + toCity + " on " + selectedDate+ ":");
                flightInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                flightInfo.setPadding(new Insets(10, 10, 10, 10));
                layoutWindow2Main.setAlignment(flightInfo, Pos.CENTER);
                if(selectedDate == null){
                    throw new NullPointerException();
                }
                layoutWindow2Main.setTop(flightInfo);
                window.setScene(scene2);
                //errorMessageLabel.setText("");
                window.show();
            }catch(NullPointerException e){
                errorMessageLabel1.setText("Please fill out all the text fields.");
            }
        }else if(event.getSource() == confirmSeatButton){
            try{
                selectedSeat = infoSeat;
                boolean check = selectedSeat == null;
                if(check || AirplaneSeat.isReserved(selectedSeat)){
                    throw new NoSeatException();
                }else{
                    
                    if(vegan.isSelected()){
                        foodOptionChosen = "Vegan";
                    }else if(vegetarian.isSelected()){
                        foodOptionChosen = "Vegetarian";
                    }else if(kosher.isSelected()){
                        foodOptionChosen = "Kosher";
                    }else if(standard.isSelected()){
                        foodOptionChosen = "Standard";
                    }else{
                        throw new NoFoodOptionException();
                    }   
                    switchToPayScene();
                }
                
            }catch(NoSeatException e){
                errorMessageLabel2.setText("Please select an available Seat");
            }catch(NoFoodOptionException e){
                errorMessageLabel2.setText("Please Select a Food Option");
            }
        }
        
        int count = 0;
        for(int r = 0; r < curPlane.numRows; r++){
            for(int c = 0; c < curPlane.numSeatsPerRow; c++){
                if(event.getSource() == seats[r][c]){
                    infoSeat = curPlane.seatList.get(count);
                    seatPosition.setText(AirplaneSeat.getName(infoSeat));
                    seatCategory.setText(AirplaneSeat.getCategory(infoSeat));
                    seatPrice.setText("$" +Double.toString(AirplaneSeat.getPrice(infoSeat)));
                    
                }
                count++;
            }
        }
    }
}
