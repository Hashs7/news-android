package me.sebhernoux.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*
import me.sebhernoux.news.fragments.ArticlesFavFragment
import me.sebhernoux.news.fragments.ArticlesFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState != null) return
        createFragment(ArticlesFragment())
        home.setOnClickListener {
            createFragment(ArticlesFragment())
        }
        fav.setOnClickListener {
            createFragment(ArticlesFavFragment())
        }
    }

    private fun createFragment(fragment: Fragment) {
        //créer une instance du fragment
//        val fragment = LocationFragment()
        // créer un transaction sur le fragment manager
        supportFragmentManager.beginTransaction().apply {
            //replacer le précédent fragment, s'il existe
            replace(R.id.fragment_container, fragment)
            //ajouter la transaction dans la stack
            addToBackStack(null)
        }.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
