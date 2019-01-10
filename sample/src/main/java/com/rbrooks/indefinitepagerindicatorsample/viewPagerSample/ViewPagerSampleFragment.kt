package com.rbrooks.indefinitepagerindicatorsample.viewPagerSample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import com.rbrooks.indefinitepagerindicatorsample.MainActivity
import com.rbrooks.indefinitepagerindicatorsample.R
import com.rbrooks.indefinitepagerindicatorsample.util.OnPagerNumberChangeListener

class ViewPagerSampleFragment : Fragment(), OnPagerNumberChangeListener, View.OnClickListener {
    private lateinit var viewPager: ViewPager

    private lateinit var pagerIndicator: IndefinitePagerIndicator
    private lateinit var verticalPagerIndicator: IndefinitePagerIndicator
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var pagerAdapter: ViewPagerAdapter? = null
    private var isVerticalEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager_sample, container, false)

        isVerticalEnabled = context?.getSharedPreferences(
            MainActivity.SHARED_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )?.getBoolean(
            MainActivity.isVerticalIndicatorKeyPreference,
            false
        ) ?: false

        bindViews(view)
        setupViews()

        return view
    }

    private fun bindViews(view: View) {
        viewPager = view.findViewById(R.id.viewpager)
        pagerIndicator = view.findViewById(R.id.viewpager_pager_indicator)
        previousButton = view.findViewById(R.id.viewpager_previous_button)
        nextButton = view.findViewById(R.id.viewpager_next_button)
        verticalPagerIndicator = view.findViewById(R.id.viewpager_vertical_pager_indicator)
    }

    private fun setupViews() {
        context?.let {
            pagerAdapter = ViewPagerAdapter(it)
            viewPager.adapter = pagerAdapter
            if (isVerticalEnabled) {
                verticalPagerIndicator.attachToViewPager(viewPager)
                verticalPagerIndicator.visibility = View.VISIBLE
            } else {
                pagerIndicator.attachToViewPager(viewPager)
                pagerIndicator.visibility = View.VISIBLE
            }
        }
        previousButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
    }

    override fun onPagerNumberChanged() {
        pagerAdapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.viewpager_previous_button -> {
                if (viewPager.currentItem == 0) {
                    viewPager.currentItem = viewPager.adapter?.let {
                        it.count - 1
                    } ?: 0
                } else {
                    viewPager.currentItem = viewPager.currentItem - 1
                }
            }
            R.id.viewpager_next_button -> {
                viewPager.adapter?.let {
                    if (viewPager.currentItem == it.count - 1) {
                        viewPager.currentItem = 0
                    } else {
                        viewPager.currentItem = viewPager.currentItem + 1
                    }
                }
            }
        }
    }
}
