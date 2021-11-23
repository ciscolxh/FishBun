package com.sangcomz.fishbun.permission

import android.Manifest.permission.*
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sangcomz.fishbun.R
import com.sangcomz.fishbun.ui.MsgDialog

/**
 * Created by sangc on 2015-10-12.
 */
class PermissionCheck(private val context: Context) {
    private fun checkPermission(permissionList: List<String>, requestCode: Int): Boolean {
        if (context !is Activity) return false

        val needRequestPermissionList = permissionList
            .map { it to ContextCompat.checkSelfPermission(context, it) }
            .filter { it.second != PackageManager.PERMISSION_GRANTED }
            .map { it.first }
            .toTypedArray()

        return if (needRequestPermissionList.isEmpty()) {
            true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,needRequestPermissionList.first())
            ) {
                ActivityCompat.requestPermissions(context, needRequestPermissionList, requestCode)
            } else {
                ActivityCompat.requestPermissions(context, needRequestPermissionList, requestCode)
            }
            false
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkStoragePermission(requestCode: Int): Boolean {
        return checkPermission(
            arrayListOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
            requestCode
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkCameraPermission(requestCode: Int): Boolean {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_PERMISSIONS
            )
            //This array contains the requested permissions.
            val permissions = info.requestedPermissions

            return if (permissions?.contains(CAMERA) == true) {
                checkPermission(listOf(CAMERA), requestCode)
            } else {
                false
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return false
        }
    }

    fun showPermissionDialog() {
        val dialog = MsgDialog(context, "无权限使用此权限", "请前往手机[设置] - [隐私] - 当前APP中开启此应用权限", true, "知道了", R.layout.dialog_left_msg)
        dialog.setClick(object : MsgDialog.OnClick {
            override fun noClick() {

            }
            override fun yesClick() {

            }
        })
        dialog.showDlg()
    }

}