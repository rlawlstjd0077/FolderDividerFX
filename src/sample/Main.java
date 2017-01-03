package sample;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class Main extends Application {
    ArrayList<String> extenstionList;
    StackPane root;
    File fs;
    @Override
    public void start(Stage primaryStage) throws Exception{

        Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.getStylesheets().add("sample/style.css");
        btnOpenDirectoryChooser.getStyleClass().add("primary-button");
        btnOpenDirectoryChooser.setText("Open DirectoryChooser");
        btnOpenDirectoryChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =
                        directoryChooser.showDialog(primaryStage);

                if(selectedDirectory == null){
                    return;
                }else{
                    fs = new File(selectedDirectory.getAbsolutePath());

                    VBox list = new VBox();
                    for(String ext : extenstionList){
                        Label label = new Label("." + ext );
                        list.getChildren().add(label);
                    }
                    list.setAlignment(Pos.CENTER);
                    root.getChildren().add(list);
                }
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(btnOpenDirectoryChooser);
        vBox.setAlignment(Pos.CENTER);
        root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("File Divider");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


    public ArrayList<String> scanExtension(File list[]){
        ArrayList<String> extensionList = new ArrayList<String>();
        String fileName = list[0].getName();
        extensionList.add(getExtension(fileName));

        for(int i = 1; i < list.length; i++){
            for(int j = 0; j < extensionList.size(); j++){
                if(getExtension(list[i].getName()).equals(extensionList.get(j))){
                    break;
                }
                if(j == extensionList.size() - 1){        //以묐났???섏? ?딆븯????
                    extensionList.add(getExtension(list[i].getName()));
                }
            }
        }
        return extensionList;
    }
    public String getExtension(String filename){
        int pos = filename.lastIndexOf( "." );
        String ext = filename.substring( pos + 1 );        // get file extension
        return ext;
    }


    public class DivideFile {
        File fs;
        public DivideFile(String path){
            fs = new File(path);
        }
        public void doSeparate(){
            if(fs.isDirectory()){
                File list[] = fs.listFiles();
                ArrayList<String> arr = scanExtension(list);        //?뚯씪 ?꾩껜 ?ㅼ틪 ?듯빐 ?뺤옣??醫낅쪟 ?뚯븙
                for(int i = 0; i < arr.size(); i++){
                    for(int j = 0; j < list.length; j++){
                        if(list[j].getName().endsWith("." + arr.get(i))){
                            moveFile(arr.get(i), list[j].getName(), fs.getPath() + "/" + list[j].getName(), fs.getPath());
                        }
                    }
                }
            }
        }

        public String moveFile(String folderName, String fileName, String beforeFilePath, String afterFilePath) {

            String path = afterFilePath+"/"+folderName;
            String filePath = path+"/"+fileName;

            File dir = new File(path);

            if (!dir.exists()) { //?대뜑 ?놁쑝硫??대뜑 ?앹꽦
                dir.mkdirs();
            }

            try{

                File file =new File(beforeFilePath);

                if(file.renameTo(new File(filePath))){ //?뚯씪 ?대룞
                    return filePath; //?깃났???깃났 ?뚯씪 寃쎈줈 return
                }else{
                    return null;
                }

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }




    }

}
