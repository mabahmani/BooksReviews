package ir.mab.booksreviews.scanner;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.Objects;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.utils.Preview;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements ScannerContract.View{
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ScannerContract.Presenter mPresenter;
    private ScannerPresenter presenter;
    private FrameLayout livePreview;
    private LinearLayout noPermissionLayout;
    private TextView enableCamera;

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

        presenter = new ScannerPresenter(this);
        livePreview = view.findViewById(R.id.livePreview);
        noPermissionLayout = view.findViewById(R.id.noPermissionLayout);
        enableCamera = view.findViewById(R.id.enableCamera);

        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.enableCameraClicked();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean hasPermissions() {
        return Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showPermissionDialog() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void loadPreview() {
        Camera camera = presenter.getCameraInstance();
        Preview preview = new Preview(getContext(), camera);
        livePreview.addView(preview);

        final int supportedWidth = camera.getParameters().getSupportedPreviewSizes().get(0).width;
        final int supportedHeight = camera.getParameters().getSupportedPreviewSizes().get(0).height;

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                FirebaseVisionImageMetadata metadata = presenter.getFirebaseVisionImageMetadata(supportedWidth,supportedHeight,FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21);
                FirebaseVisionImage image = FirebaseVisionImage.fromByteArray(data,metadata);
                mPresenter.detectBarcode(image);
            }
        });
    }

    @Override
    public void setPresenter(ScannerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA){
            if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                noPermissionLayout.setVisibility(View.GONE);
                presenter.start();
            }

            else {
                noPermissionLayout.setVisibility(View.VISIBLE);
            }
        }

    }
}


