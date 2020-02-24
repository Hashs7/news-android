package me.sebhernoux.dmii.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.computation_fragment.*
import me.sebhernoux.dmii.R

class LocationFragment : Fragment() {

    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getPosition()
                    //La permission est accordée, on adapte la vue en conséquence
                } else {
                    //Permission refusée, on laisse l'utilisateur tranquille
                    //On peut désactiver certaines fonctionnalités de l'app
                    // On affiche un message d'erreur
                    showDialog("Sale merde")
                    resultLabel.setText("Sale merde")
                }
            }
            else -> {
                // Le code ne concerne pas la permission, on l'ignore
            }
        }
    }

    private fun showDialog(
        message: String,
        positiveCallback: () -> Unit = {},
        negativeCallback: () -> Unit = {}
    ) {
        val context = context ?: return
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Androidly Alert")
        builder.setMessage(message)
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                context,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
            positiveCallback()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                context,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
            negativeCallback()
        }
        builder.show()
    }

    private fun checkPermissions() {
        val activity = activity ?: return
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // la permission n’est pas accordée
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // L’utilisateur a déjà refusé la permission, on doit lui expliquer pourquoi on en a besoin; une dialog, une vue dédiée etc…
                showDialog("On a besoin de ta permission pour récupérer ta position", positiveCallback = {
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_CODE
                    )
                })

            } else {
                //Demander la permission
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_CODE
                )
            }
        } else {
            //La permission est accordée
            getPosition()
        }
    }

    private fun getPosition() {
        val activity = activity ?: return
        Toast.makeText(context, "Get current position", Toast.LENGTH_SHORT).show()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            resultLabel.setText("Votre position est ${it.latitude}, ${it.longitude}")
        }
    }

    companion object {
        const val PERMISSION_CODE = 1
    }
}