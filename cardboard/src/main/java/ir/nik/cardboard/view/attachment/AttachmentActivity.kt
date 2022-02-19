package ir.nik.cardboard.view.attachment

import android.os.Bundle
import com.nik.cardboard.R
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.base.BaseActivity

internal class AttachmentActivity : BaseActivity() {

    private var dcId: Long? = null
    private var relatedTableId: Long? = null
    private var relatedTableName: String? = null

    override fun setup() {
        dcId = intent.extras?.getLong(Const.KEY_DC_ID)
        relatedTableId = intent.extras?.getLong(Const.KEY_RELATED_TABLE_ID)
        relatedTableName = intent.extras?.getString(Const.KEY_RELATED_TABLE_NAME)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_attachment)
        super.onCreate(savedInstanceState)

        replaceFragmentInActivity(
            R.id.container,
            AttachmentFragment(
                dcId ?: 0,
                relatedTableId ?: 0,
                object : AttachmentFragment.OnActionListener {
                    override fun onAddClick() {
                        gotoAddAttachment()
                    }
                }
            ),
            AttachmentFragment.TAG
        )
    }

    private fun gotoAddAttachment() {
        replaceFragmentInActivity(
            R.id.container,
            AddAttachmentFragment(
                relatedTableId ?: 0,
                relatedTableName ?: "",
                dcId?: 0,
            ),
            AddAttachmentFragment.TAG
        )
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else
            this.finish()
    }
}
