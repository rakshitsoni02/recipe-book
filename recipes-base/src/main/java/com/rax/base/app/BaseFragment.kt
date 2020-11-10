package com.rax.base.app

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rax.base.R
import dagger.hilt.android.AndroidEntryPoint


/**
 * Base fragment providing common config
 */
@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    private val permissionRequestsMap: PermissionRequests? by lazy {
        activity?.let { ViewModelProvider(it).get(PermissionRequests::class.java) }
    }
    fun requestPermission(
        permissionType: String,
        requestCode: Int,
        onGrantedListener: (permissionAlertShown: Boolean) -> Unit,
        onDeniedListener: (permissionAlertShown: Boolean) -> Unit
    ) {
        val context = context ?: return
        when (ContextCompat.checkSelfPermission(context, permissionType)) {
            PackageManager.PERMISSION_GRANTED -> {
                onGrantedListener.invoke(false)
            }
            PackageManager.PERMISSION_DENIED -> {
                // Permission is not granted
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(permissionType)) {
                    onDeniedListener.invoke(false)
                } else {
                    permissionRequestsMap?.set(requestCode, PermissionRequest(
                        permission = permissionType,
                        requestCode = requestCode,
                        onGrantedListener = onGrantedListener,
                        onDeniedListener = onDeniedListener
                    ))
                    // No explanation needed, we can request the permission.
                    requestPermissions(
                        arrayOf(permissionType),
                        requestCode
                    )
                }
            }
        }
    }
    class PermissionRequests : ViewModel() {
        private val requests = mutableMapOf<Int, PermissionRequest>()

        operator fun get(requestCode: Int) = requests[requestCode]
        operator fun set(requestCode: Int, value: PermissionRequest) {
            requests[requestCode] = value
        }

        fun remove(requestCode: Int) = requests.remove(requestCode)
    }
    @Suppress("unused")
    class PermissionRequest(
        val permission: String,
        val requestCode: Int,
        val onGrantedListener: (permissionAlertShown: Boolean) -> Unit,
        val onDeniedListener: (permissionAlertShown: Boolean) -> Unit
    )

    companion object {
        fun show(fragment: BaseFragment, activity: BaseActivity?) {
            if (null == activity) return
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}