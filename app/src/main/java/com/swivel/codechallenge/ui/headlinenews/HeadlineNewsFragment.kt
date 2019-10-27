package com.swivel.codechallenge.ui.headlinenews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.swivel.codechallenge.R
import com.swivel.codechallenge.di.ComponentInjector
import com.swivel.codechallenge.model.NewsResponse
import com.swivel.codechallenge.util.AppHelper
import kotlinx.android.synthetic.main.fragment_headline_news.*

class HeadlineNewsFragment : Fragment() {

    private lateinit var headlineViewModel: HeadlineNewsViewModel
    internal lateinit var context: Context
    lateinit var rvHeadlineNews: RecyclerView
    private lateinit var recyclerAdapterHeadlineNews: RecyclerAdapterHeadlineNews
    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headlineViewModel =
            ViewModelProviders.of(activity!!).get(HeadlineNewsViewModel::class.java).also {
                ComponentInjector.component.inject(it)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_headline_news, container, false)
        this.context = this.activity!!
        rvHeadlineNews = root.findViewById(R.id.rvHeadlineNews)

        recyclerAdapterHeadlineNews = RecyclerAdapterHeadlineNews(context, headlineViewModel)
        rvHeadlineNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@HeadlineNewsFragment.recyclerAdapterHeadlineNews

        }

        if (AppHelper.isInternetAvailable(context)) {
            attachObserver()
            headlineViewModel.getHeadlineNews()
        } else {
            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT)
                .show()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (AppHelper.isInternetAvailable(context)) {
            attachObserver()
            headlineViewModel.getHeadlineNews()
        } else {
            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun attachObserver() {
        headlineViewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let { showLoadingDialog(it) }
        })
        headlineViewModel.apiError.observe(viewLifecycleOwner, Observer<Throwable> {
            it?.let {
                Snackbar.make(rvHeadlineNews, it.localizedMessage, Snackbar.LENGTH_LONG).show()
            }
        })
        headlineViewModel.newsResponse.observe(viewLifecycleOwner, Observer<NewsResponse> {
            it?.let {
                recyclerAdapterHeadlineNews.notifyDataSetChanged()
            }
        })
    }

    private fun showLoadingDialog(show: Boolean) {
        if (show) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): HeadlineNewsFragment {
            return HeadlineNewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}