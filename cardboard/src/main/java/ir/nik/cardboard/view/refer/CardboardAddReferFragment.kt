package ir.nik.cardboard.view.refer

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showDateDialog
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.showFlashbar
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.ChooseDialog
import ir.nik.cardboard.data.network.model.request.CardboardBaseTypeRequest
import ir.nik.cardboard.data.network.model.request.CardboardProcessStepRequest
import ir.nik.cardboard.data.network.model.request.CardboardReferenceActionRequest
import ir.nik.cardboard.data.network.model.request.CardboardReferenceTypeRequest
import ir.nik.cardboard.data.network.model.utt.CardboardUttWfsModel
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.dialogreceiver.CardboardReceiverDialog
import kotlinx.android.synthetic.main.fragment_add_refer_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CardboardAddReferFragment(
    private val wfsCrID: Long,
    private val wfsProcessId: Long,
    private val wfsProcessTableName: String,
    private val callback: (CardboardReferModel, CardboardUttWfsModel) -> Unit
) : CardboardBaseFragment() {

    private val viewModel by viewModel<CardboardReferViewModel>()
    private lateinit var referModel: CardboardReferModel

    private var listReferenceAction: MutableList<ItemModel> = mutableListOf()
    private var listReference: MutableList<ItemModel> = mutableListOf()
    private var listDocumentPriority: MutableList<ItemModel> = mutableListOf()
    private var listProcessStep: MutableList<ItemModel> = mutableListOf()
    private var listDocumentNature: MutableList<ItemModel> = mutableListOf()

    private var deliveryID: Long = -1
    private var referenceID: Long = -1
    private var priorityId: Long = -1
    private var processStepId: Long = -1
    private var receiverId: Long = -1
    private var referNatureId: Long = -1

    override fun setup() {
        referModel = CardboardReferModel()
        txtAnswerTime.text = viewModel.currentDate
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_refer_cardboard, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_done, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done ->
                saveReferSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    override fun handleOnClickListeners() {
        val activity = activity ?: return

        layoutReferenceAction.setOnClickListener {
            getReferenceAction()
        }
        layoutReferenceType.setOnClickListener {
            getReferenceType()
        }
        layoutPriority.setOnClickListener {
            getPriority()
        }
        layoutProcessStep.setOnClickListener {
            getProcessStep()
        }
        layoutDocumentNature.setOnClickListener {
            getDocumentNature()
        }
        layoutReceiver.setOnClickListener {
            if (processStepId != -1L)
                CardboardReceiverDialog(wfsCrID, processStepId) { model ->
                    referModel.receiver = model.userName ?: ""
                    referModel.icon = model.thumbnail ?: ""
                    txtReceiver.text = "${model.userName}"
                    receiverId = model.userId ?: -1
                }.show(activity.supportFragmentManager, CardboardReceiverDialog.TAG)
            else
                activity.showFlashbar(
                    "",
                    getString(R.string.fill_next_step),
                    MessageStatus.ERROR
                )
        }

        /*      layoutLetterType.setOnClickListener { showDialog(R.array.list_letter_type) }
                layoutUrgency.setOnClickListener { showDialog(R.array.list_urgency) }
                layoutReferralType.setOnClickListener { showDialog(R.array.list_referral_type) }*/
        layoutAnswerTime.setOnClickListener {
            activity.showDateDialog {
                txtAnswerTime.text = it
            }
        }
        btnGoogleSpeech.setOnClickListener { showSpeechDialog() }
    }

    override fun handleObservers() {
        /*  viewModel.deliveryResponse.observe(viewLifecycleOwner,{ response ->
              listDelivery = mutableListOf<ItemModel>().apply {
                  response.result?.forEach { result ->
                      val id = result.dtId ?: -1
                      val name = result.name ?: ""
                      if (id != -1L)
                          add(ItemModel(id, name))
                  }
              }
              showDeliveryList()
          })*/
        viewModel.referenceActionResponse.observe(viewLifecycleOwner, {
            prcReferenceAction.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if (list.isEmpty())
                        txtReferenceAction.text = "داده ای برای نمایش وجود ندارد"
                    else {
                        list.forEach { model ->
                            listReferenceAction.apply {
                                add(
                                    ItemModel(
                                        model.valueMember ?: 0,
                                        model.textMember ?: ""
                                    )
                                )
                            }
                        }
                        showReferenceActionList()
                    }
                } ?: kotlin.run {
                    txtReferenceAction.text = "داده ای برای نمایش وجود ندارد"
                }
            }
        })
        viewModel.referTypeResponse.observe(viewLifecycleOwner, { response ->
            prcReferenceType.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { list ->
                    if (list.isEmpty())
                        txtReferenceType.text = "داده ای برای نمایش وجود ندارد"
                    else {
                        listReference = mutableListOf<ItemModel>().apply {
                            list.forEach { result ->
                                val id = result.valueMember ?: -1
                                val name = result.textMember ?: ""
                                if (id != -1L)
                                    add(ItemModel(id, name))
                            }
                        }
                        showReferenceType()
                    }
                } ?: kotlin.run {
                    txtReferenceType.text = "داده ای برای نمایش وجود ندارد"
                }
            }
        })
        viewModel.processStepResponse.observe(viewLifecycleOwner, { response ->
            prcProcessStep.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { list ->
                    if (list.isEmpty())
                        txtProcessStep.text = "داده ای برای نمایش وجود ندارد"
                    else {
                        list.let {
                            if (response.result.size == 1) {
                                txtProcessStep.text = response.result[0].wfsPsName
                                processStepId = response.result[0].wfsPsId ?: 0

                            } else if (response.result.size != 0) {
                                listProcessStep = mutableListOf<ItemModel>().apply {
                                    response.result.forEach { result ->
                                        val id = result.wfsPsId ?: -1
                                        val name = result.wfsPsName ?: ""
                                        if (id != -1L)
                                            add(ItemModel(id, name))
                                    }
                                }
                                showProcessStep()
                            }
                        }
                    }
                } ?: kotlin.run {
                    txtProcessStep.text = "داده ای برای نمایش وجود ندارد"
                }
            }
        })

        viewModel.priorityResponse.observe(viewLifecycleOwner, { response ->
            prcPriority.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { list ->
                    if (list.isEmpty())
                        txtPriority.text = "داده ای برای نمایش وجود ندارد"
                    else {
                        listDocumentPriority = mutableListOf<ItemModel>().apply {
                            list.forEach { result ->
                                val id = result.baseId ?: -1
                                val name = result.baseName ?: ""
                                if (id != -1L)
                                    add(ItemModel(id, name))
                            }
                        }
                        showPriority()
                    }
                } ?: kotlin.run {
                    txtPriority.text = "داده ای برای نمایش وجود ندارد"
                }
            }
        })

        viewModel.referNatureResponse.observe(viewLifecycleOwner, { response ->
            prcDocumentNature.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { list ->
                    if (list.isEmpty())
                        txtDocumentNature.text = "داده ای برای نمایش وجود ندارد"
                    else {
                        listDocumentNature = mutableListOf<ItemModel>().apply {
                            list.forEach { result ->
                                val id = result.baseId ?: -1
                                val name = result.baseName ?: ""
                                if (id != -1L)
                                    add(ItemModel(id, name))
                            }
                        }
                        showDocumentNature()
                    }
                } ?: kotlin.run {
                    txtDocumentNature.text = "داده ای برای نمایش وجود ندارد"
                }
            }
        })
    }

    private fun getProcessStep() {
        if (listProcessStep.isEmpty()) {
            prcProcessStep.isVisible = true
            viewModel.getProcessStepList(
                CardboardProcessStepRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 10
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = processStepJson(
                        wfsCrID,
                        wfsProcessId,
                        wfsProcessTableName
                    )
                }
            )
        } else
            showProcessStep()
    }

    private fun showProcessStep() {
        val activity = activity ?: return
        ChooseDialog(listProcessStep) {
            processStepId = it.id
            txtProcessStep.text = it.title
        }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }


    private fun getPriority() {
        if (listDocumentPriority.isEmpty()) {
            prcPriority.isVisible = true
            viewModel.getPriority(
                CardboardBaseTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = baseTypeJson(Const.BaseType.PRIORITY)
                }
            )
        } else
            showPriority()
    }

    private fun showPriority() {
        val activity = activity ?: return

        ChooseDialog(listDocumentPriority) {
            priorityId = it.id
            txtPriority.text = it.title
        }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getDocumentNature() {
        if (listDocumentNature.isEmpty()) {
            prcDocumentNature.isVisible = true
            viewModel.getReferNature(
                CardboardBaseTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = baseTypeJson(Const.BaseType.REFER_NATURE)
                }
            )
        } else
            showDocumentNature()
    }

    private fun showDocumentNature() {
        val activity = activity ?: return

        ChooseDialog(listDocumentNature) {
            referNatureId = it.id
            txtDocumentNature.text = it.title
        }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }


    private fun getReferenceAction() {
        if (listReferenceAction.isEmpty()) {
            prcReferenceAction.isVisible = true
            viewModel.getReferenceActionList(
                CardboardReferenceActionRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 10
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = referenceActionJson()
                }
            )
        } else
            showReferenceActionList()
    }

    private fun showReferenceActionList() {
        val activity = activity ?: return
        ChooseDialog(listReferenceAction) {
            deliveryID = it.id
            txtReferenceAction.text = it.title
        }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getReferenceType() {
        if (listReference.isEmpty()) {
            prcReferenceType.isVisible = true
            viewModel.getReferTypeList(
                CardboardReferenceTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = referTypeJson()
                }
            )
        } else
            showReferenceType()
    }

    private fun showReferenceType() {
        val activity = activity ?: return
        ChooseDialog(listReference) {
            referenceID = it.id
            txtReferenceType.text = it.title
        }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun showSpeechDialog() {
        val activity = activity ?: return
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa")
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )

        if (intent.resolveActivity(activity.packageManager) != null)
            startActivityForResult(
                intent,
                SPEECH_CODE
            )
        else
            activity.showFlashbar(
                getString(R.string.warning),
                getString(R.string.not_support),
                MessageStatus.ERROR
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null)
            when (requestCode) {
                SPEECH_CODE -> {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    edtReferralMessage.setText(result?.get(0))
                }
            }
    }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return
        viewModel.error.observe(viewLifecycleOwner, {
            hideLoading()
            activity.showError(it?.message)
        })
    }

    private fun hideLoading() {
        prcReferenceAction.isVisible = false
        prcReferenceType.isVisible = false
        prcProcessStep.isVisible = false
        prcDocumentNature.isVisible = false
        prcPriority.isVisible = false
    }

    private fun saveReferSettings() {
        val activity = activity ?: return

        if (isValid) {
            callback.invoke(referModel, CardboardUttWfsModel().also { model ->
                model.rowNum = 1
                model.wfsProcessId = wfsProcessId
                model.userIdSender = viewModel.userId
                model.userIdReceiver = receiverId
                model.postIdSender = 0
                model.postIdReceiver = 0
                model.wfsNextId = processStepId
                model.wfsCrPriorityId = priorityId
                model.wfsRtId = referenceID
                model.wfsDt = deliveryID
                model.wfsCrReply = edtReferralMessage.text.toString()
                model.wfsCrNote = ""
                model.wfsCrPursuitDateTime = txtAnswerTime.text.toString()
                model.wfsCrType = 0
                model.wfsCrStatus = 0
                model.isValid = 0
                model.wfsCrSubject = txtDocumentNature.text.toString()
                model.wfsNrId = referNatureId
            })
        } else
            activity.yToast(
                getString(R.string.fill_all_blanks),
                MessageStatus.ERROR
            )
    }

    private val isValid: Boolean
        get() {
            if (deliveryID != -1L &&
                referenceID != -1L &&
                processStepId != -1L &&
                priorityId != -1L &&
                receiverId != -1L &&
                referNatureId != -1L
            )
                return true
            return false
        }

    companion object {
        private const val SPEECH_CODE = 10
        val TAG = "${Const.APP_NAME}: ${CardboardAddReferFragment::class.java.simpleName}"
    }
}