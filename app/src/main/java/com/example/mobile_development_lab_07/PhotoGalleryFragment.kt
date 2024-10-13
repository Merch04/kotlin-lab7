package com.example.mobile_development_lab_07

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView
//    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация ViewModel
        photoGalleryViewModel = ViewModelProvider(this)[PhotoGalleryViewModel::class.java]

        // Создание обработчика для основного потока
//        val responseHandler = Handler(Looper.getMainLooper())

//        thumbnailDownloader = ThumbnailDownloader(responseHandler) { photoHolder, bitmap ->
//            val drawable = BitmapDrawable(resources, bitmap)
//            photoHolder.bindDrawable(drawable)
//        }

//        lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)
//        lifecycle.addObserver(thumbnailDownloader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

//        viewLifecycleOwner.lifecycle.addObserver(
//            thumbnailDownloader.viewLifecycleObserver
//        )
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдение за LiveData из ViewModel
        photoGalleryViewModel.galleryItemLiveData.observe(viewLifecycleOwner) { galleryItems ->
            photoRecyclerView.adapter = PhotoAdapter(galleryItems)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Очистка запросов из очереди при уничтожении фрагмента
        //thumbnailDownloader.clearQueue()

        //lifecycle.removeObserver(thumbnailDownloader.fragmentLifecycleObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        viewLifecycleOwner.lifecycle.removeObserver(
//            thumbnailDownloader.viewLifecycleObserver
//        )
    }

    private class PhotoHolder(private val itemImageView: ImageView) : RecyclerView.ViewHolder(itemImageView) {
//        val bindDrawable: (Drawable) -> Unit = itemImageView::setImageDrawable

        fun bindGalleryItem(galleryItem:
                            GalleryItem) {
            Picasso.get()
                .load(galleryItem.url)
                .placeholder(R.drawable.bill_up_close)
                .into(itemImageView)
        }
    }

    private inner class PhotoAdapter(private val galleryItems: List<GalleryItem>) : RecyclerView.Adapter<PhotoHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_gallery,
                    parent,
                    false
                ) as ImageView
            return PhotoHolder(view)
        }

        override fun getItemCount(): Int = galleryItems.size

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = galleryItems[position]
//            val placeholder: Drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bill_up_close) ?: ColorDrawable()
//            holder.bindDrawable(placeholder)
//            thumbnailDownloader.queueThumbnail(holder, galleryItem.url)
            holder.bindGalleryItem(galleryItem)
            // Здесь можно установить фактическое изображение из galleryItem.
            // Пример:
            // Glide.with(holder.itemImageView.context).load(galleryItem.imageUrl).into(holder.itemImageView)
        }
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}
