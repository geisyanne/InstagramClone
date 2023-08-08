package co.geisyanne.instagram.post.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import co.geisyanne.instagram.R
import co.geisyanne.instagram.add.view.AddActivity
import co.geisyanne.instagram.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private var addListener: AddListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("takePhotoKey") { requestKey, bundle ->   // PASSAR URI PARA PROX TELA (AddActivity)
            val uri = bundle.getParcelable<Uri>("uri")
            uri?.let {
                val intent = Intent(requireContext(), AddActivity::class.java)
                intent.putExtra("photoUri", uri)
                addActivityResult.launch(intent) // PRA ATV POSTERIOR NOTIFICAR EVENTOS DISPARADOS LÁ COM setResult(RESULT_OK)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddListener) {
            addListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddBinding.bind(view)

        if (savedInstanceState == null )
            setupViews()
    }

    private fun setupViews() {
        val tabLayout = binding?.addTab
        val viewPager = binding?.addViewpager
        val adapter = AddViewPagerAdapter(requireActivity())
        viewPager?.adapter = adapter

        if (tabLayout != null && viewPager != null) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {  // LIGAR CAMERA NOVAMENTE, DEPOIS DE TROCAR ABA
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text == getString(adapter.tabs[0])) {
                        startCamera()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
            TabLayoutMediator(
                tabLayout,
                viewPager
            ) { tab, position ->    // DEVOLVE UM CALLBACK COM A ABA ATUAL E A POSIÇÃO SELECIONADA
                tab.text = getString(adapter.tabs[position]) // ALTERAR TEXTO DA ABA ATUAL
            }.attach()
        }

        if (allPermissionsGrated()) {
            startCamera()
        } else {
            getPermission.launch(REQUIRED_PERMISSION)
        }
    }

    private fun startCamera() {
        setFragmentResult("cameraKey", bundleOf("startCamera" to true))  // COMUNICAÇÃO ENTRE FRAG AddFragment-CameraFragment
    }

    private val addActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {  // CALLBACK RETORNA DA AddActivity
            addListener?.onPostCreated()  // PARA NOTIFICAR O LISTENER Q AddActivity FOI FINALIZADA
        }
    }

    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (allPermissionsGrated()) {  // DOUBLE CHECK
                startCamera()
            } else {
                Toast.makeText(requireContext(), R.string.permission_camera_denied, Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGrated() =
        ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[1]) == PackageManager.PERMISSION_GRANTED

    interface AddListener {
        fun onPostCreated()
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}