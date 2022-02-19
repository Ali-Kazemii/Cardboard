package ir.nik.cardboard.view.createletter

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.showFlashbar
import ir.nik.cardboard.data.network.model.request.PostFinalSaveLetterRequest
import ir.nik.cardboard.data.network.model.response.CaseReferralListByWFSCaseIdResponse
import ir.nik.cardboard.data.network.model.response.DraftLetterResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.attachment.AttachmentActivity
import ir.nik.cardboard.view.attachment.AttachmentType
import ir.nik.cardboard.view.base.BaseActivity
import ir.nik.cardboard.view.createletter.draft.LetterDraftFragment
import ir.nik.cardboard.view.createletter.extra.LetterExtraInformationFragment
import ir.nik.cardboard.view.createletter.information.LetterInformationFragment
import ir.nik.cardboard.view.createletter.linked.add.AddLetterLinkedFragment
import ir.nik.cardboard.view.createletter.linked.list.LetterLinkedFragment
import ir.nik.cardboard.view.createletter.receiver.add.AddLetterReceiverFragment
import ir.nik.cardboard.view.createletter.receiver.list.LetterReceiverFragment
import ir.nik.cardboard.view.createletter.step.LetterStepFragment
import kotlinx.android.synthetic.main.activity_create_letter.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CreateLetterActivity : BaseActivity() {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var formId: Int = 0
    private var customerId: Long = 0
    private var wfsCaseId: Long = 0
    private var letterId: Long = 0
    private var letterEditModel: DraftLetterResponse.Result? = null
    private var isEditMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_letter)

        gotoDraftLetters()
    }

    override fun handleObservers() {
        viewModel.responseId.observe(this, { response ->
            actionLoading.isVisible = false

            response.result?.let {
                if (it != 0L) {
                    showFlashbar(
                        getString(R.string.success),
                        response.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )
                    onBackPressed()

                } else {
                    failureOperation(response.message)
                }
            } ?: kotlin.run {
                failureOperation(response.message)
            }
        })
    }

    private fun gotoDraftLetters() {
        replaceFragmentInActivity(
            R.id.container,
            LetterDraftFragment(
                object : LetterDraftFragment.OnActionListener {
                    override fun onAdd() {
                        isEditMode = false
//                        wfsCaseId = model.wfsCaseId ?: 0
                        gotoSteps()
                    }

                    override fun onEdit(model: DraftLetterResponse.Result) {
                        wfsCaseId = model.wfsCaseId ?: 0
                        letterId = model.letterId ?: 0
                        formId = model.formId ?: 0
                        customerId = model.customerId ?: 0

                        isEditMode = true
                        letterEditModel = model
                        gotoEditStep()
                    }

                    override fun onLetterInformation(letterId: Long) {
                        gotoEditLetterInformation(letterId)
                    }

                    override fun onAttachment(letterId: Long) {
                        gotoAttachment(letterId)
                    }

                    override fun onReceivers(wfsCaseId: Long) {
                        gotoLetterReceiver(wfsCaseId)
                    }

                    override fun onLetterLinked(wfsCaseId: Long) {
                        gotoLinkedLetter(wfsCaseId)
                    }

                    override fun onLetterExtraInformation(letterId: Long, customerId: Long) {
                        gotoLetterExtraInformation(letterId, customerId)
                    }
                }
            ),
            LetterDraftFragment.TAG
        )
    }

    private fun gotoSteps() {
        replaceFragmentInActivity(
            R.id.container,
            LetterStepFragment {
                gotoChooseStep(it)
            },
            LetterStepFragment.TAG
        )
    }

    private fun gotoChooseStep(step: String) {
        when (step) {
            Const.LetterStep.KEY_LETTER_INFORMATION -> {
                if (isEditMode)
                    gotoEditLetterInformation(letterId)
                else
                    gotoLetterInformation()
            }
            Const.LetterStep.KEY_LETTER_ATTACHMENT -> gotoAttachment(letterId)
            Const.LetterStep.KEY_LETTER_RECEIVER -> gotoLetterReceiver(wfsCaseId)
            Const.LetterStep.KEY_LETTER_LINKED -> gotoLinkedLetter(wfsCaseId)
            Const.LetterStep.KEY_LETTER_EXTRA_INFORMATION -> gotoLetterExtraInformation(
                letterId,
                customerId
            )
            Const.LetterStep.KEY_LETTER_SEND -> sendLetter()
        }
    }

    private fun sendLetter() {
        actionLoading.isVisible = true
        viewModel.postFinalSaveLetter(
            PostFinalSaveLetterRequest().also { request ->
                request.oasLetterId = letterId
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 1
            }
        )
    }

    private fun gotoAttachment(letterId: Long) {
        val intent = Intent(this@CreateLetterActivity, AttachmentActivity::class.java)
        val bundle = Bundle()
        bundle.putLong(Const.KEY_DC_ID, Const.Letter.KEY_LETTER_DC_ID)
        bundle.putLong(Const.KEY_RELATED_TABLE_ID, letterId)
        bundle.putString(Const.KEY_RELATED_TABLE_NAME, Const.LetterTableName.LETTER)
        bundle.putSerializable(
            Const.KEY_ATTACHMENT_TYPE,
            AttachmentType.WITH_ADD_ATTACHMENT
        )
        intent.putExtras(bundle)
        startActivity(intent)
    }


    private fun gotoLetterInformation() {
        replaceFragmentInActivity(
            R.id.container,
            LetterInformationFragment { formId, customerId ->
                this.formId = formId
                this.customerId = customerId
            },
            LetterInformationFragment.TAG
        )
    }

    private fun gotoEditLetterInformation(letterId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            LetterInformationFragment(letterId) { formId, customerId ->
                this.formId = formId
                this.customerId = customerId
            },
            LetterInformationFragment.TAG
        )
    }


    private fun gotoEditStep() {
        replaceFragmentInActivity(
            R.id.container,
            LetterStepFragment(letterEditModel) {
                gotoChooseStep(it)
            },
            LetterStepFragment.TAG
        )
    }

    private fun gotoLetterExtraInformation(letterId: Long, customerId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            LetterExtraInformationFragment(letterId, customerId),
            LetterExtraInformationFragment.TAG
        )
    }

    private fun gotoLinkedLetter(wfsCaseId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            LetterLinkedFragment(
                wfsCaseId,
                object : LetterLinkedFragment.OnActionListener {
                    override fun onAdd() {
                        gotoAddLinkedLetter()
                    }
                }
            ),
            LetterLinkedFragment.TAG
        )
    }

    private fun gotoAddLinkedLetter() {
        replaceFragmentInActivity(
            R.id.container,
            AddLetterLinkedFragment(wfsCaseId),
            AddLetterLinkedFragment.TAG
        )
    }

    private fun gotoLetterReceiver(wfsCaseId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            LetterReceiverFragment(
                wfsCaseId,
                object : LetterReceiverFragment.OnActionListener {
                    override fun onAdd() {
                        gotoAddLetterReceiver(wfsCaseId)
                    }

                    override fun onEdit(model: CaseReferralListByWFSCaseIdResponse.Result) {
                        editLetterReceiver(model)
                    }
                }
            ),
            LetterReceiverFragment.TAG
        )
    }

    private fun editLetterReceiver(model: CaseReferralListByWFSCaseIdResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            AddLetterReceiverFragment(wfsCaseId, model),
            AddLetterReceiverFragment.TAG
        )
    }

    private fun gotoAddLetterReceiver(wfsCaseId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            AddLetterReceiverFragment(wfsCaseId),
            AddLetterReceiverFragment.TAG
        )
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            actionLoading.isVisible = false
            showError(it?.message)
        })
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            this.finish()
    }

}