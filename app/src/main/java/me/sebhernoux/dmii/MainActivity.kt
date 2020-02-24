package me.sebhernoux.dmii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.sebhernoux.dmii.fragments.ChoiceFragment
import me.sebhernoux.dmii.fragments.LocationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) return
        createFragment()
    }

    private fun createFragment() {
        //créer une instance du fragment
        val fragment = ChoiceFragment()
//        val fragment = LocationFragment()
        // créer un transaction sur le fragment manager
        supportFragmentManager.beginTransaction().apply {
            //replacer le précédent fragment, s'il existe
            replace(R.id.fragment_container, fragment)
            //ajouter la transaction dans la stack
            addToBackStack(null)
        }.commit()
    }

}
