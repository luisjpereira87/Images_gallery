package com.example.lacticoop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.lacticoop.models.Photo;
import com.example.lacticoop.repository.PhotoRepository;
import com.example.lacticoop.services.DownloadImageService;
import com.example.lacticoop.services.PhotoService;
import com.example.lacticoop.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    private PhotoRepository photoRepository;
    List<Photo> photoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.renderGridLayout();
    }

    public void renderGridLayout(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.ImagesTable);

        photoRepository = new PhotoRepository(this);
        photoRepository.cleanTable();
        try {
            List<Photo> photoList = new PhotoService().getAll();

            for (int i = 0; i < photoList.size(); i++){
                if(i == 10){
                    break;
                }
                photoRepository.addPhoto(photoList.get(i));
            }

            List<List<Photo>> parts = Utils.divideList(photoRepository.allPhotos(), 3);
            int row = 0;

            for(List<Photo> t : parts){
                int column = 0;
                for(Photo photo : t){
                    ImageView imageView = new ImageView(this);

                    GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 20;
                    param.topMargin = 20;
                    param.setGravity(Gravity.CENTER);
                    param.columnSpec = GridLayout.spec(column);
                    param.rowSpec = GridLayout.spec(row);
                    imageView.setLayoutParams (param);
                    gridLayout.addView(imageView);

                    new DownloadImageService().get("https://live.staticflickr.com/"+photo.getServer()+"/"+photo.getPhotoId()+"_"+photo.getSecret()+"_w.jpg", imageView);
                    column++;
                }
                row++;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}