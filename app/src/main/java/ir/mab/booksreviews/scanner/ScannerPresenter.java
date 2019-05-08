package ir.mab.booksreviews.scanner;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class ScannerPresenter implements ScannerContract.Presenter {

    private ScannerContract.View mScannerView;
    private Detector detector;

    ScannerPresenter(ScannerContract.View scannerView) {
        this.mScannerView = scannerView;
        scannerView.setPresenter(this);
    }

    public ScannerPresenter(){

    }


    @Override
    public void detectBarcode(FirebaseVisionImage image) {
        detector = new Detector(mScannerView,this);
        detector.execute(image);
    }

    @Override
    public void enableCameraClicked() {
        mScannerView.showPermissionDialog();
    }

    @Override
    public void start() {
        if (mScannerView.hasPermissions()){
            mScannerView.setupCamera();
            mScannerView.loadPreview();
        }

        else {
            mScannerView.showPermissionDialog();
        }
    }

    @Override
    public void stop() {
        Log.d("AminStop","Stopped!");
        if (detector != null)
            detector.cancel(false);
    }

    private static FirebaseVisionBarcodeDetector getFirebaseVisionBarcodeDetector() {

        FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_EAN_13,
                        FirebaseVisionBarcode.FORMAT_EAN_8)
                .build();

        return FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);
    }

    static class Detector extends AsyncTask<FirebaseVisionImage,Boolean,Integer> {

        private ScannerContract.View view;
        private ScannerContract.Presenter presenter;

        Detector(ScannerContract.View view, ScannerContract.Presenter presenter) {
            this.view = view;
            this.presenter = presenter;
        }

        @Override
        protected Integer doInBackground(FirebaseVisionImage... firebaseVisionImages) {
            Log.d("BARCODE","do back");
            getFirebaseVisionBarcodeDetector().detectInImage(firebaseVisionImages[0])
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                            // Task completed successfully
                            // ...
                            for (FirebaseVisionBarcode barcode : barcodes){
                                Log.d("BARCODE: ",barcode.getRawValue());
                            }
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });
            return null;
        }
    }
}