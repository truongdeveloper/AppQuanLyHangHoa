package com.example.appquanlyhanghoa.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appquanlyhanghoa.R;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

public class ScanFragment extends Fragment {

    public static final int DEFAULT_VIEW = 0x22;
    private static final int REQUEST_CODE_SCAN = 0X01;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan,container,false);

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] !=
//                PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        if (requestCode == DEFAULT_VIEW) {
////            A new screen will open to scan barcode and the control will go to Scan kit SDK.
////            HmsScan.CODE128_SCAN_TYPE
//            ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN, new
//                    HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE,
//                    HmsScan.CODE128_SCAN_TYPE).create());
////Once the barcode is detected the control will again come back to parent application inside
////            onActivityResult() method.
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK || data == null) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_SCAN) {
//            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
////result u will get
//            if (obj instanceof HmsScan) {
//                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
//                    Toast.makeText(getActivity(), ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
//                }
//                return;
//            }
//        }
//    }
}
