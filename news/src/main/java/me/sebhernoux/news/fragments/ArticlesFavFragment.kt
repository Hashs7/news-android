package me.sebhernoux.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.articles_list_fragment.*
import me.sebhernoux.news.R
import me.sebhernoux.news.adapters.ArticleAdapter
import me.sebhernoux.news.models.Article
import me.sebhernoux.news.viewmodels.ArticlesViewModel

class ArticlesFavFragment: Fragment() {
    lateinit var viewModel: ArticlesViewModel
    private val adapterRecycler = ArticleAdapter {
        changeFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(ArticlesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articles_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //définir l'orientation des élements (vertical)
        recyclerView.layoutManager = LinearLayoutManager(context)
        //associer l'adapter à la recyclerview
        recyclerView.adapter = adapterRecycler

        viewModel.listArticles.observe(viewLifecycleOwner, Observer {
            val favArticles = it.filter {article -> article.favorite }
            adapterRecycler.updateData(favArticles)
        })
    }

    private fun changeFragment(article: Article) {
        val fragment = ArticleDetailFragment.newInstance(article)
        // créer un transaction sur le fragment manager
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            //replacer le précédent fragment, s'il existe
            replace(R.id.fragment_container, fragment)
            //ajouter la transaction dans la stack
            addToBackStack(null)
        }?.commit()
    }
}