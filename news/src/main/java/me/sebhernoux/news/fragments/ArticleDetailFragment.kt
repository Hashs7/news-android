package me.sebhernoux.news.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_detail_fragment.*
import me.sebhernoux.news.R
import me.sebhernoux.news.models.Article
import me.sebhernoux.news.viewmodels.ArticlesViewModel

class ArticleDetailFragment: Fragment() {
    private lateinit var viewModel: ArticlesViewModel

    val article: Article by lazy {
        arguments?.getParcelable<Article>("article")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Le view Model est rattaché au cycle de vie de l’activité
        // Tant que l’activité existe, le view model existe aussi
        // Quand l’activité se détruit, le view Model se détruit également
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)

        return inflater.inflate(R.layout.article_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article_title.text = article.title
        article_source.text = article.source.name
        article_author.text = article.author
        Picasso.get().load(article.urlToImage).into(article_image)

        material_fav.icon = ContextCompat.getDrawable(view.context, if(article.favorite) R.drawable.ic_star_fill else R.drawable.ic_star_border)
        material_fav.setOnClickListener {
            viewModel.favArticle(article)
            material_fav.icon = ContextCompat.getDrawable(view.context, if(article.favorite) R.drawable.ic_star_fill else R.drawable.ic_star_border)
        }
        material_original.setOnClickListener { openBrowser() }
        material_share.setOnClickListener { share() }
    }

    private fun share() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, article.url);
        startActivity(Intent.createChooser(shareIntent,getString(R.string.send_to)))
    }

    private fun openBrowser() {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse(article.url)
        startActivity(openURL)
    }

    companion object {
        fun newInstance(article: Article): ArticleDetailFragment {
           return ArticleDetailFragment().apply {
               arguments = bundleOf("article" to article)
           }
        }
    }
}