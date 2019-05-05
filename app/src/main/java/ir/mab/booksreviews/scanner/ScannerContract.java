package ir.mab.booksreviews.scanner;

import android.hardware.Camera;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;

public interface ScannerContract {

    interface View extends BaseView<Presenter>{

        boolean hasPermissions();

        void showPermissionDialog();

        void loadPreview();

        void loadNoCameraAvailable();

    }

    interface Presenter extends BasePresenter{

        FirebaseVisionBarcodeDetector getFirebaseVisionBarcodeDetector();

        FirebaseVisionImageMetadata getFirebaseVisionImageMetadata(int width,int height,int imageFormat);

        Camera getCameraInstance();

        void detectBarcode(FirebaseVisionImage image);

    }
}
