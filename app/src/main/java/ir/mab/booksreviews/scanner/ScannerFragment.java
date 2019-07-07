package ir.mab.booksreviews.scanner;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.book_detail.BookDetailsActivity;
import ir.mab.booksreviews.history.Barcode;
import ir.mab.booksreviews.history.BarcodeList;
import ir.mab.booksreviews.utils.Preview;
import ir.mab.booksreviews.utils.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements ScannerContract.View{

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ScannerContract.Presenter mPresenter;
    private FrameLayout livePreview;
    private LinearLayout noPermissionLayout;
    private TextView enableCamera;
    private Camera camera;
    private Preview preview;
    private ImageView viewFinder;
    private LinearLayout barcodeScaneLine;



    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new ScannerPresenter(this);
        livePreview = view.findViewById(R.id.livePreview);
        viewFinder = view.findViewById(R.id.viewFinder);
        barcodeScaneLine = view.findViewById(R.id.barcodeScanLine);
        noPermissionLayout = view.findViewById(R.id.noPermissionLayout);
        enableCamera = view.findViewById(R.id.enableCamera);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.enableCameraClicked();
            }
        });

        Animation blink = AnimationUtils.loadAnimation(getActivity(),R.anim.blink);
        barcodeScaneLine.setAnimation(blink);
        viewFinder.setAnimation(blink);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean hasPermissions() {
        return Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED ;
    }

    @Override
    public void showPermissionDialog() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void loadPreview() {
        preview = new Preview(getContext(), camera);
        livePreview.addView(preview);
    }

    @Override
    public void intentBookDeatilsActivity(String isbn) {
        Barcode barcode = new Barcode();
        barcode.setIsbn(isbn);
        Date currentTime = Calendar.getInstance().getTime();
        barcode.setDate(currentTime);
        BarcodeList barcodeList = SharedPref.getInstance(getActivity()).getBarcodeList();
        barcodeList.addBarcode(barcode);
        SharedPref.getInstance(getActivity()).putBarcodeList(barcodeList);

        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);

        intent.putExtra("isbn",isbn);

        startActivity(intent);

        getActivity().finish();

    }

    @Override
    public void setupCamera(){

        camera = getCameraInstance();

        final int supportedWidth = camera.getParameters().getSupportedPreviewSizes().get(0).width;
        final int supportedHeight = camera.getParameters().getSupportedPreviewSizes().get(0).height;

        final FirebaseVisionImageMetadata metadata =
                new FirebaseVisionImageMetadata.Builder()
                        .setWidth(supportedWidth)   // 480x360 is typically sufficient for
                        .setHeight(supportedHeight)  // image recognition
                        .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                        .build();

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                FirebaseVisionImage image = FirebaseVisionImage.fromByteArray(data,metadata);
                mPresenter.detectBarcode(image);
            }
        });
    }

    private Camera getCameraInstance() {
        if (camera == null) {
            // move to view
            try {
                camera = Camera.open(); // attempt to get a Camera instance
            } catch (Exception e) {
                // Camera is not available (in use or does not exist)
            }
        }

        return camera;
    }

    @Override
    public void setPresenter(ScannerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA){
            if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                noPermissionLayout.setVisibility(View.GONE);
                mPresenter.start();
            }

            else {
                noPermissionLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void releaseCameraAndPreview() {
        preview = null;
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //releaseCameraAndPreview();
    }
}


