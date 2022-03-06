package ir.nik.cardboard.view.createletter.receiver.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ChooseDialog
import ir.awlrhm.modules.view.Spinner
import ir.awlrhm.modules.view.searchablePagingDialog.DialogStatus
import ir.awlrhm.modules.view.searchablePagingDialog.SearchablePagingDialog
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.CardboardCaseReferralListByWFSCaseIdResponse
import ir.nik.cardboard.data.network.model.response.CardboardUserListResponse
import ir.nik.cardboard.data.network.model.utt.CardboardUttWfsModel
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.contain_letter_receiver_cardboard.*
import kotlinx.android.synthetic.main.fragment_letter_add_receiver_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AddLetterReceiverFragment(
    private val wfsCaseId: Long
) : CardboardBaseFragment(), OnStatusListener {

    constructor(
        caseId: Long,
        model: CardboardCaseReferralListByWFSCaseIdResponse.Result?
    ) : this(caseId) {
        this.model = model
    }

    private val viewModel by viewModel<CreateLetterViewModel>()
    private var formId: Int = 553
    private var wfsCrId: Long = 0
    private var model: CardboardCaseReferralListByWFSCaseIdResponse.Result? = null

    private lateinit var userDialog: SearchablePagingDialog<CardboardUserListResponse.Result>
    private var userDialogStatus = DialogStatus.CLICKED

    private var listReferType: MutableList<ItemModel> = mutableListOf()
    private var listSendReceiveMethod: MutableList<ItemModel> = mutableListOf()
    private var listPriority: MutableList<ItemModel> = mutableListOf()
    private var listReferenceAction: MutableList<ItemModel> = mutableListOf()

    private var customerId: Long = -1
    private var customerPostId: Long = -1
    private var priorityId: Long = -1
    private var referTypeId: Long = -1
    private var sendReceiveMethodId: Int = -1
    private var referenceActionId: Long = -1


    override fun setup() {
        model?.let { model ->
            wfsCrId = model.wfsCrId ?: 0
            customerId = model.receiverCustomer ?: 0
            customerPostId = model.receiverPostId ?: 0
            priorityId = model.priorityId ?: 0
            referTypeId = model.rtId ?: 0
            referenceActionId = model.dtId ?: 0
            sendReceiveMethodId = model.crType ?: 0

            spPriority.text = model.priorityName
            spReferenceAction.text = model.dtName
            spReferType.text = model.rtName
            spSendReceiveMethod.text = model.crTypeName
            spReceiver.text = model.receiverUser
            edtReferDescription.setText(model.crReply)
            txtTitle.text = getString(R.string.edit_receiver)
        } ?: kotlin.run {
            txtTitle.text = getString(R.string.add_receiver)
        }

        userDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<CardboardUserListResponse.Result> {
                override fun onChoose(model: DynamicModel<CardboardUserListResponse.Result>) {
                    customerId = model.dynamic.userId ?: 0
                    customerPostId = model.dynamic.postId ?: 0
                    spReceiver.text = model.title
                }

                override fun onDismiss() {
                    userDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    viewModel.getUserList(
                        CardboardUserDataRequest().also { request ->
                            request.userId = viewModel.userId
                            request.registerUserId = viewModel.userId
                            request.typeOperation = 105
                            request.financialYearId = viewModel.financialYear
                            request.pageNumber = pageNumber
                            request.jsonParameters = letterInformationJson(formId, search)
                        }
                    )
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_add_receiver_cardboard, container, false)
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.userListResponse.observe(viewLifecycleOwner, { response ->
            spReceiver.loading(false)
            if (userDialogStatus != DialogStatus.CLICKED)
                return@observe

            response.result?.let { list ->
                val listUser =
                    mutableListOf<DynamicModel<CardboardUserListResponse.Result>>()
                listUser.also { listModel ->
                    list.forEach { item ->
                        listModel.add(
                            DynamicModel(item.customerFullTitle ?: "", item)
                        )
                    }
                }
                if (listUser.isNotEmpty()) {
                    userDialog.setSource(response.resultCount ?: 0, listUser)
                    if (!userDialog.isVisible)
                        userDialog.show(
                            activity.supportFragmentManager,
                            SearchablePagingDialog.TAG
                        )
                } else if (userDialog.isVisible)
                    userDialog.showNoData()
                else
                    spReceiver.failedData()

            } ?: kotlin.run {
                spReceiver.failedData()
            }
        })
        viewModel.referTypeResponse.observe(viewLifecycleOwner, {
            spReferType.loading(false)
            it.result?.let { list ->
                list.forEach { model ->
                    listReferType.apply {
                        add(
                            ItemModel(
                                model.valueMember ?: 0,
                                model.textMember ?: ""
                            )
                        )
                    }
                }
                showReferType()

            } ?: kotlin.run {
                spReferType.failedData()
            }
        })
        viewModel.documentMethodTypeResponse.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                list.forEach { model ->
                    listSendReceiveMethod.apply {
                        add(
                            ItemModel(model.wfsDtId ?: 0, model.wfsDtName ?: "")
                        )
                    }
                }
                showSendReceiveMethod()

            } ?: kotlin.run {
                spSendReceiveMethod.failedData()
            }
        })
        viewModel.priorityResponse.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                list.forEach { model ->
                    listPriority.apply {
                        add(
                            ItemModel(model.baseId ?: 0, model.baseName ?: "")
                        )
                    }
                }
                showPriority()

            } ?: kotlin.run {
                spPriority.failedData()
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, { response ->
            response.result?.let {
                if (it != 0L) {
                    onStatus(Status.SUCCESS)
                    activity.showFlashbar(
                        getString(R.string.success),
                        response.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )
                } else
                    activity.failureOperation(response.message)
            } ?: kotlin.run {
                activity.failureOperation(response.message)
            }
        })
        viewModel.referenceActionResponse.observe(viewLifecycleOwner, {
            spReferenceAction.loading(false)
            it.result?.let { list ->
                if (list.isEmpty())
                    spReferenceAction.failedData()
                else {
                    list.forEach { model ->
                        listReferenceAction.add(
                            ItemModel(
                                model.valueMember ?: 0,
                                model.textMember ?: ""
                            )
                        )

                    }
                    showReferenceActionList()
                }
            } ?: kotlin.run {
                spReferenceAction.failedData()
            }
        })
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return
        spReferType.setOnClickListener {
            getReferType()
        }
        spSendReceiveMethod.setOnClickListener {
            getSendReceiveMethod()
        }
        spPriority.setOnClickListener {
            getPriority()
        }
        spReceiver.setOnClickListener {
            getUserList()
        }
        spReferenceAction.setOnClickListener {
            getReferenceActionList()
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                onStatus(Status.LOADING)
                viewModel.postCaseReferWithJson(
                    CardboardCaseReferWithJsonRequest().also { request ->
                        request.wfsCrIdJson =
                            convertUTTWFSModelToJson(
                                mutableListOf<CardboardUttWfsModel>().apply {
                                    add(CardboardUttWfsModel().also { model ->
                                        model.wfsCrId = wfsCrId
                                        model.wfsCaseId = wfsCaseId
                                        model.wfsProcessId = formId.toLong()
                                        model.userIdReceiver = customerId
                                        model.postIdReceiver = customerPostId
                                        model.userIdSender = viewModel.userId
                                        model.postIdSender = viewModel.postId
                                        model.wfsNextId = 0
                                        model.wfsPreviousId = 0
                                        model.wfsRtId = referTypeId
                                        model.wfsDt = referenceActionId
                                        model.wfsCrReply = edtReferDescription.text.toString()
                                        model.wfsCrType = sendReceiveMethodId
                                        model.wfsCrSubject = spReferType.text ?: ""
                                        model.wfsRaId = referenceActionId
                                    })
                                })
                        request.typeOperation = Const.ReferOtherOperation.KEY_NEXT_STEP
                        request.financialYearId = viewModel.financialYear
                        request.userId = viewModel.userId
                    }
                )
            } else {
                onStatus(Status.FAILURE)
                activity.yToast(
                    getString(R.string.fill_all_blanks),
                    MessageStatus.ERROR
                )
            }
        }
    }

    private fun getUserList() {
        spReceiver.loading(true)
        userDialogStatus = DialogStatus.CLICKED
        viewModel.getUserList(
            CardboardUserDataRequest().also { request ->
                request.userId = viewModel.userId
                request.registerUserId = viewModel.userId
                request.typeOperation = 105
                request.financialYearId = viewModel.financialYear
                request.pageNumber = 1
                request.jsonParameters = letterInformationJson(formId, "")
            }
        )
    }

    private fun getReferenceActionList() {
        if (listReferenceAction.isEmpty()) {
            spReferenceAction.loading(true)
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

        spReferenceAction.showData(
            listReferenceAction,
            activity
        ) {
            referenceActionId = it.id
        }
    }

    private fun getPriority() {
        if (listPriority.isEmpty()) {
            spPriority.loading(true)
            viewModel.getPriority(
                CardboardBaseTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 10
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = baseTypeJson(Const.BaseType.PRIORITY)
                }
            )
        } else
            showPriority()
    }

    private fun showPriority() {
        val activity = activity ?: return

        spPriority.loading(false)
        if (listPriority.isNotEmpty())
            ChooseDialog(listPriority) {
                spPriority.text = it.title
                this.priorityId = it.id

            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getSendReceiveMethod() {
        if (listSendReceiveMethod.isEmpty()) {
            spSendReceiveMethod.loading(true)
            viewModel.getDocumentSendReceiveMethod(
                CardboardDocumentSendReceiveMethodRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = documentSendReceiveMethodJson()
                }
            )
        } else
            showSendReceiveMethod()
    }

    private fun showSendReceiveMethod() {
        val activity = activity ?: return

        spSendReceiveMethod.loading(false)
        if (listSendReceiveMethod.isNotEmpty())
            ChooseDialog(listSendReceiveMethod) {
                spSendReceiveMethod.text = it.title
                this.sendReceiveMethodId = it.id.toInt()

            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getReferType() {
        if (listReferType.isEmpty()) {
            spReferType.loading(true)
            viewModel.getReferTypeList(
                CardboardReferenceTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 10
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = referTypeJson()
                }
            )
        } else
            showReferType()
    }

    private fun showReferType() {
        val activity = activity ?: return

        if (listReferType.isNotEmpty())
            ChooseDialog(listReferType) {
                spReferType.text = it.title
                this.referTypeId = it.id

            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private val isValid: Boolean
        get() {
            return customerId != -1L
                    && priorityId != -1L
                    && referTypeId != -1L
                    && sendReceiveMethodId != -1
                    && referenceActionId != -1L
        }

    override fun onStatus(status: Status) {
        when (status) {
            Status.LOADING -> {
                prcDone.isVisible = true
                btnDone.isVisible = false
            }
            else -> {
                prcDone.isVisible = false
                btnDone.isVisible = true
            }
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            onStatus(Status.FAILURE)

            layoutParent.children.forEach { child ->
                if(child is Spinner)
                    child.loading(false)
            }

            activity?.showError(it?.message)
        })
        viewModel.errorPostCaseRefer.observe(this, {
            onStatus(Status.FAILURE)
            activity?.showError(it?.message)
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddLetterReceiverFragment::class.java.simpleName}"
    }
}