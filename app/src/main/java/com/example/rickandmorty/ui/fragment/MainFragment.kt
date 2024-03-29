package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentMainBinding
import com.example.rickandmorty.ui.model.app_view_model.NavigationViewModel
import com.example.rickandmorty.ui.model.app_view_model.ScrollViewModel
import com.example.rickandmorty.ui.model.request_view_model.CharactersListViewModel
import com.example.rickandmorty.ui.recycleview.adapter.CharactersListAdapter
import com.example.rickandmorty.ui.recycleview.core.BaseAdapter
import com.example.rickandmorty.ui.recycleview.decorations.GroupVerticalItemDecoration
import com.example.rickandmorty.ui.recycleview.model.CharactersListDataModel
import com.example.rickandmorty.utils.BackButtonListener
import com.example.rickandmorty.utils.common.extensions.insetterRecyclerBottom
import com.example.rickandmorty.utils.common.extensions.px
import com.example.rickandmorty.utils.nullOnDestroy
import dagger.hilt.android.AndroidEntryPoint
import com.example.rickandmorty.utils.cicerone.Screens.characterItem
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment(), BackButtonListener {

    private var binding: FragmentMainBinding by nullOnDestroy()

    private val navigationViewModel by viewModels<NavigationViewModel>()
    private val charactersListViewModel by viewModels<CharactersListViewModel>()
    private val scrollViewModel by activityViewModels<ScrollViewModel>()

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment().apply {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.charactersListViewModel = charactersListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.insetterRecyclerBottom()

        val adapter = BaseAdapter(
            listOf(
                CharactersListAdapter(::onCharactersClick, ::onFavoriteClick)
            )
        )

        lifecycleScope.launchWhenCreated {
            binding.recycler.setHasFixedSize(true)
            binding.recycler.layoutManager?.isItemPrefetchEnabled = true
            binding.recycler.setItemViewCacheSize(-1)
            binding.recycler.adapter = adapter
            binding.recycler.addItemDecoration(
                GroupVerticalItemDecoration(
                    R.layout.item_characters_list,
                    0,
                    12.px
                )
            )
        }

        // старт загрузки данных с сервера
        charactersListViewModel.stateLoadingView.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.swipeRefresh.isRefreshing = true
            }
        }

        // загрузка прошла успешно
        charactersListViewModel.stateSuccessView.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // принудительное обновление списка адаптера
        binding.swipeRefresh.setOnRefreshListener {
            charactersListViewModel.updateCharactersList()
        }

        // прокручиваем адаптер на сохраненное положение
        scrollViewModel.recycleItemPositionView.observe(viewLifecycleOwner) {
            binding.recycler.layoutManager?.onRestoreInstanceState(it)
        }

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager =
                    LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val totalItemCount = layoutManager?.itemCount ?: 0
                val lastVisible = layoutManager?.findLastVisibleItemPosition() ?: 0
                val endHasBeenReached = lastVisible + 5 > totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    charactersListViewModel.charactersListLoadMore()
                }
            }
        })
    }

    private fun onCharactersClick(newItem: CharactersListDataModel) {
        navigationViewModel.onForwardCommandClick(
            characterItem(
                CharacterItemDataModel(
                    id = newItem.id,
                    name = newItem.name,
                    status = newItem.status,
                    species = newItem.species,
                    type = newItem.type,
                    gender = newItem.gender,
                    image = newItem.image,
                    name_location = newItem.name_location
                )
            )
        )
    }

    private fun onFavoriteClick(newItem: CharactersListDataModel) {
        charactersListViewModel.updateShareData(newItem, !newItem.favorite)
    }

    override fun onBackPressed(): Boolean {
        navigationViewModel.onBackCommandClick()
        return true
    }
}