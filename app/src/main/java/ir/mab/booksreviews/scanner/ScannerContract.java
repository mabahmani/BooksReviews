package ir.mab.booksreviews.scanner;

import android.hardware.Camera;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;

public interface ScannerContract {

    interface View extends BaseView<Presenter>{

        boolean hasPermissions();

        void showPermissionDialog();

        void loadPreview();

    }

    interface Presenter extends BasePresenter{

        FirebaseVisionImageMetadata getFirebaseVisionImageMetadata(int width,int height,int imageFormat);

        Camera getCameraInstance();

        void detectBarcode(FirebaseVisionImage image);

    }
}
