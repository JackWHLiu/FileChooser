package com.lwh.filechooser

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.SectionIndexer
import android.widget.TextView
import com.lwh.filechooser.fs.F
import com.lwh.filechooser.util.PinyinComparator
import com.lwh.fileexplorer.R
import com.lwh.jackknife.util.TextUtils
import com.lwh.jackknife.util.TimeUtils
import com.lwh.jackknife.widget.CommonAdapter
import com.lwh.jackknife.widget.ViewHolder
import java.util.*

class FileAdapter(context: Context) : CommonAdapter<F>(context), SectionIndexer {

    var iv_file_type: ImageView? = null
    var tv_file_name: TextView? = null
    var tv_file_last_modified: TextView? = null

    override fun addItems(datas: MutableList<F>) {
        super.addItems(generateLetters(datas))
    }

    private fun generateLetters(fileables: MutableList<F>): MutableList<F> {
        for (fileable in fileables) {
            val sortLetter = TextUtils.getPinyinFromSentence(fileable.name.substring(0, 1))
                    .toUpperCase()
            fileable.sortLetter = sortLetter
        }
        Collections.sort(fileables, PinyinComparator.get())
        return fileables
    }

    override fun getSections(): Array<Any> {
        return arrayOf(0)
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        for (i in 0 .. count) {
            val sortLetter = datas!![i]!!.sortLetter
            if (sortLetter != null) {
                val first = sortLetter.toUpperCase()[0]
                if (first.toInt() == sectionIndex) {
                    return i
                }
            }
        }
        return -1
    }

    override fun getSectionForPosition(position: Int): Int {
        return if (datas!![position]!!.sortLetter == null) -1 else datas!![position]!!.sortLetter.toUpperCase()[0].toInt()
    }

    fun getPositionForSection(sectionIndex: Char): Int {
        for (i in 0 until count) {
            val sortLetter = datas!![i]!!.sortLetter
            if (sortLetter != null) {
                val first = sortLetter.toUpperCase()[0]
                if (first == sectionIndex) {
                    return i
                }
            }
        }
        return -1
    }

    override val itemLayoutId: Int
        protected get() = R.layout.item_file

    override val itemViewIds: IntArray
        protected get() = intArrayOf(
                R.id.iv_file_type,
                R.id.tv_file_name,
                R.id.tv_file_last_modified
        )

    override fun <VIEW : View> onBindViewHolder(position: Int, data: F, holder: ViewHolder<VIEW>) {
        iv_file_type = holder.findViewById(R.id.iv_file_type) as ImageView
        tv_file_name = holder.findViewById(R.id.tv_file_name) as TextView
        tv_file_last_modified = holder.findViewById(R.id.tv_file_last_modified) as TextView
        tv_file_name!!.text = data.name
        tv_file_last_modified!!.text = TimeUtils.getString(data.lastModified(), TimeUtils.FORMAT_DATE_2)
        if (data.isFolder) {
            iv_file_type!!.setImageResource(R.drawable.icon_mime_group_folder)
        } else {
            setFileIcon(iv_file_type!!, data.name.substring(data.name.lastIndexOf(".") + 1))
        }
    }

    /**
     * 设置显示的图标。
     *
     * @param suffix 文件后缀
     */
    private fun setFileIcon(iconView: ImageView, suffix: String) {
        var suffix = suffix
        suffix = suffix.toUpperCase(Locale.CHINA)
        if (suffix == "MP3" || suffix == "AAC" || suffix == "FLAC") {
            iconView.setImageResource(R.drawable.icon_mime_group_music)
            return
        }
        if (suffix == "MP4" || suffix == "AVI" || suffix == "FLV" || suffix == "MPEG" || suffix == "MOV") {
            iconView.setImageResource(R.drawable.icon_mime_group_movie)
            return
        }
        if (suffix == "JPG" || suffix == "GIF" || suffix == "PNG" || suffix == "JPEG" || suffix == "BMP") {
            iconView.setImageResource(R.drawable.icon_mime_group_photo)
            return
        }
        if (suffix == "ZIP" || suffix == "RAR" || suffix == "7Z") {
            iconView.setImageResource(R.drawable.icon_mime_group_compress)
            return
        }
        if (suffix == "APK") {
            iconView.setImageResource(R.drawable.icon_mime_apk)
            return
        }
        if (suffix == "TXT") {
            iconView.setImageResource(R.drawable.icon_mime_txt)
            return
        }
        if (suffix == "EPUB") {
            iconView.setImageResource(R.drawable.icon_mime_group_text)
            return
        }
        if (suffix == "DOC" || suffix == "DOCX" || suffix == "WPS") {
            iconView.setImageResource(R.drawable.icon_mime_group_doc)
            return
        }
        if (suffix == "XLS" || suffix == "XLSX" || suffix == "ET") {
            iconView.setImageResource(R.drawable.icon_mime_group_excel)
            return
        }
        if (suffix == "PPT" || suffix == "PPTX" || suffix == "DPS") {
            iconView.setImageResource(R.drawable.icon_mime_group_ppt)
            return
        }
        if (suffix == "PDF") {
            iconView.setImageResource(R.drawable.icon_mime_pdf)
            return
        }
        if (suffix == "HTML") {
            iconView.setImageResource(R.drawable.icon_mime_html)
            return
        }
        if (suffix == "CHM") {
            iconView.setImageResource(R.drawable.icon_mime_chm)
            return
        }
        if (suffix == "URL") {
            iconView.setImageResource(R.drawable.icon_mime_url)
            return
        }
    }
}