package com.swivel.codechallenge.ui.customnews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.savvyapps.togglebuttonlayout.ToggleButtonLayout
import com.swivel.codechallenge.R
import com.swivel.codechallenge.di.ComponentInjector
import com.swivel.codechallenge.model.NewsResponse
import com.swivel.codechallenge.util.API_KEY
import com.swivel.codechallenge.util.AppHelper
import kotlinx.android.synthetic.main.fragment_headline_news.*
import java.text.SimpleDateFormat
import java.util.*

class CustomNewsFragment : Fragment() {

    private lateinit var customNewsViewModel: CustomNewsViewModel
    internal lateinit var context: Context
    lateinit var rvCustomNews: RecyclerView
    private lateinit var recyclerAdapterCustomNews: RecyclerAdapterCustomNews
    private var networkUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customNewsViewModel = ViewModelProviders.of(activity!!).get(CustomNewsViewModel::class.java).also {
            ComponentInjector.component.injectCustomNews(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_custom_news, container, false)
        val toggleButtonLayout = root.findViewById(R.id.toggle_button_layout) as ToggleButtonLayout
        rvCustomNews = root.findViewById(R.id.rvCustomNews)
        this.context = this.activity!!

        recyclerAdapterCustomNews = RecyclerAdapterCustomNews(context, customNewsViewModel)
        rvCustomNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@CustomNewsFragment.recyclerAdapterCustomNews
        }
        //For news API need to pass last earlier day
        val cal = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, -30)
        val fromDate = df.format(cal.time)
        networkUrl = "everything?q=bitcoin&from=$fromDate&sortBy=publishedAt&apiKey=$API_KEY"
        //Custom news toggle button
        toggleButtonLayout.onToggledListener = { toggle, selected , isSelected->
            if (AppHelper.isInternetAvailable(context)) {
                attachObserver()
                networkUrl = "everything?q="+selected.title+"&from="+fromDate+"&sortBy=publishedAt&apiKey="+ API_KEY
                customNewsViewModel.getCustomNews(networkUrl)
            } else {
                Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (AppHelper.isInternetAvailable(context)) {
            attachObserver()
            customNewsViewModel.getCustomNews(networkUrl)
        } else {
            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun attachObserver() {
        customNewsViewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            it?.let { showLoadingDialog(it) }
        })
        customNewsViewModel.apiError.observe(viewLifecycleOwner, Observer<Throwable> {
            it?.let {
                Snackbar.make(rvCustomNews, it.localizedMessage, Snackbar.LENGTH_LONG).show()
            }
        })
        customNewsViewModel.newsResponse.observe(viewLifecycleOwner, Observer<NewsResponse> {
            it?.let {
                recyclerAdapterCustomNews.notifyDataSetChanged()
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
        fun newInstance(sectionNumber: Int): CustomNewsFragment {
            return CustomNewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}