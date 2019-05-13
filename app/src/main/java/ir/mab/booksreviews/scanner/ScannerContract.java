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

        void setupCamera();

        void loadPreview();

        void intentBookDeatilsActivity(String isbn);

        void releaseCameraAndPreview();

    }

    interface Presenter extends BasePresenter{

        void detectBarcode(FirebaseVisionImage image);

        void enableCameraClicked();

    }
}
