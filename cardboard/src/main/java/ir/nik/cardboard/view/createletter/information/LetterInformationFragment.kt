package ir.nik.cardboard.view.createletter.information

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
import ir.nik.cardboard.data.network.model.response.CardboardLetterByIdResponse
import ir.nik.cardboard.data.network.model.response.CardboardUserListResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.utils.baseTypeJson
import ir.nik.cardboard.utils.documentSendReceiveMethodJson
import ir.nik.cardboard.utils.letterInformationJson
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.contain_letter_information.*
import kotlinx.android.synthetic.main.fragment_letter_information.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterInformationFragment(
    private val callback: (Int, Long) -> Unit,
) : CardboardBaseFragment(), OnStatusListener {

    constructor(
        letterId: Long,
        callback: (Int, Long) -> Unit
    ) : this(callback) {
        this.letterId = letterId
    }

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var userStatus: UserStatus? = null
    private var letterStatus: LetterStatus = LetterStatus.INTERNAL

    private lateinit var userDialog: SearchablePagingDialog<CardboardUserListResponse.Result>
    private var userDialogStatus = DialogStatus.CLICKED

    private var listSecretariat: MutableList<ItemModel> = mutableListOf()
    private var listAccessType: MutableList<ItemModel> = mutableListOf()
    private var listPriority: MutableList<ItemModel> = mutableListOf()
    private var listDocumentMethodType: MutableList<ItemModel> = mutableListOf()

    private var formId: Int? = Const.LetterType.KEY_INTERNAL
    private var letterId: Long? = 0
    private var secretariatId: Long? = -1
    private var atId: Long? = -1
    private var priorityId: Long? = -1
    private var customerId: Long = 0
    private var documentMethodType: Long? = -1

    override fun setup() {
        letterId?.let { id ->
            if (id != 0L) {
                waitLoading(true)
                viewModel.getLetterById(
                    CardboardLetterByIdRequest().also { request ->
                        request.oasLetterId = id
                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.typeOperation = 2
                    }
                )
            }
        }

        userDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<CardboardUserListResponse.Result> {
                override fun onChoose(model: DynamicModel<CardboardUserListResponse.Result>) {
                    when (userStatus) {
                        UserStatus.SENDER -> {
                            spLetterSender.loading(false)
                            spLetterSender.text = model.title
                        }
                       else -> {
                            spLetterReceiver.loading(false)
                            spLetterReceiver.text = model.title
                        }
                    }
                    customerId = model.dynamic.customerId ?: 0
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
                            request.jsonParameters = letterInformationJson(formId ?: 0, search)
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
        return inflater.inflate(R.layout.fragment_letter_information, container, false)
    }


    override fun handleOnClickListeners() {
        val activity = activity ?: return
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                onStatus(Status.LOADING)
                viewModel.postLetter(
                    CardboardPostLetterRequest().also { request ->
                        request.letterId = this.letterId
                        request.secretariatId = this.secretariatId
                        request.formId = this.formId
                        request.atId = this.atId
                        request.priorityId = this.priorityId
                        request.creatorUserId = viewModel.userId
                        request.creatorPostId = viewModel.postId
                        request.letterSubject = edtSubject.text.toString()
                        request.letterTextBody = edtBody.text.toString()
                        request.letterDate = viewModel.currentDate

                        if (letterStatus != LetterStatus.INTERNAL)
                            request.customerId = this.customerId

                        if (letterStatus == LetterStatus.IMPORTED) {
                            request.otherCustomer = edtLetterOtherSender.text.toString()
                            request.inLetterDate = dvImportedLetter.date
                            request.inLetterNumber = edtImportedLetterNo.text.toString()
                            request.documentMethodType = this.documentMethodType

                        } else if (letterStatus == LetterStatus.EXPORTED)
                            request.otherCustomer = edtLetterOtherReceiver.text.toString()

                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.registerDate = viewModel.currentDate
                    }
                )
            } else {
                onStatus(Status.FAILURE)
                activity.showFlashbar(
                    "",
                    getString(R.string.fill_all_blanks),
                    MessageStatus.ERROR
                )
            }
        }
        rdbExported.setOnCheckedChangeListener { _, b ->
            if (b) {
                letterStatus = LetterStatus.EXPORTED
                formId = Const.LetterType.KEY_EXPORTED
                setChangeLetterStatus()
            }
        }
        rdbImported.setOnCheckedChangeListener { _, b ->
            if (b) {
                letterStatus = LetterStatus.IMPORTED
                formId = Const.LetterType.KEY_IMPORTED
                setChangeLetterStatus()
            }
        }
        rdbInternal.setOnCheckedChangeListener { _, b ->
            if (b) {
                letterStatus = LetterStatus.INTERNAL
                formId = Const.LetterType.KEY_INTERNAL
                setChangeLetterStatus()
            }
        }
        spSecretariat.setOnClickListener {
            getSecretariat()
        }
        dvImportedLetter.setOnClickListener {
            activity.showDateDialog {
                dvImportedLetter.date = formatDate(it)
            }
        }
        spAccessType.setOnClickListener {
            getAccessTypeList()
        }
        spPriority.setOnClickListener {
            getPriority()
        }
        spLetterSender.setOnClickListener {
            userStatus = UserStatus.SENDER
            getUserList()
        }
        spLetterReceiver.setOnClickListener {
            userStatus = UserStatus.RECEIVER
            getUserList()
        }
        spDocumentMethodType.setOnClickListener {
            getDocumentMethodType()
        }
        spReceivedType.setOnClickListener {
            getDocumentMethodType()
        }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.letterByIdResponse.observe(viewLifecycleOwner,{
            waitLoading(false)
            it.result?.let { list ->
                setEditValue(list[0])

            } ?: kotlin.run {
                activity.showFlashbar(
                    getString(R.string.error),
                    it.message ?: getString(R.string.response_error),
                    MessageStatus.ERROR
                )
            }
        })
        viewModel.secretariatResponse.observe(viewLifecycleOwner,{
            it.result?.let { result ->
                result.forEach { item ->
                    listSecretariat.apply {
                        add(
                            ItemModel(item.secretariatId ?: 0, item.name ?: "")
                        )
                    }
                }
                showSecretariatList()

            } ?: kotlin.run {
                spSecretariat.failedData()
            }
        })
        viewModel.listAccessTypeResponse.observe(viewLifecycleOwner,{
            it.result?.let { result ->
                result.forEach { item ->
                    listAccessType.apply {
                        add(
                            ItemModel(item.atId ?: 0, item.atName ?: "")
                        )
                    }
                }
                showAccessTypeList()

            } ?: kotlin.run {
                spSecretariat.failedData()
            }
        })
        viewModel.priorityResponse.observe(viewLifecycleOwner,{
            it.result?.let { result ->
                result.forEach { item ->
                    listPriority.apply {
                        add(
                            ItemModel(item.baseId ?: 0, item.baseName ?: "")
                        )
                    }
                }
                showPriority()

            } ?: kotlin.run {
                spPriority.failedData()
            }
        })
        viewModel.userListResponse.observe(viewLifecycleOwner,{ response ->
            when (userStatus) {
                UserStatus.SENDER -> spLetterSender.loading(false)
                else -> {spLetterReceiver.loading(false)}
            }
            if (userDialogStatus != DialogStatus.CLICKED)
                return@observe

            response.result?.let { list ->
                if (list.isNotEmpty()) {
                    val listUser =
                        mutableListOf<DynamicModel<CardboardUserListResponse.Result>>()
                    listUser.also { listModel ->
                        list.forEach { item ->
                            listModel.add(
                                DynamicModel(item.customerFullTitle ?: "", item)
                            )
                        }
                    }
                    userDialog.setSource(response.resultCount ?: 0, listUser)
                    if (!userDialog.isVisible)
                        userDialog.show(
                            activity.supportFragmentManager,
                            SearchablePagingDialog.TAG
                        )
                }

            } ?: kotlin.run {
                spSecretariat.failedData()
            }
        })
        viewModel.documentMethodTypeResponse.observe(viewLifecycleOwner,{
            it.result?.let { result ->
                result.forEach { item ->
                    listDocumentMethodType.apply {
                        add(
                            ItemModel(item.wfsDtId ?: 0, item.wfsDtName ?: "")
                        )
                    }
                }
                showDocumentMethodType()
            } ?: kotlin.run {
                (if (letterStatus == LetterStatus.IMPORTED)
                        spReceivedType
                    else spDocumentMethodType
                ).failedData()
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner,{
            if (it.result != 0L) {
                onStatus(Status.SUCCESS)
                activity.successOperation(it.message)
                callback.invoke(formId ?: 0, customerId)
                activity.onBackPressed()

            } else {
                activity.failureOperation(it.message)
            }
        })
    }

    private fun setEditValue(
        model: CardboardLetterByIdResponse.Result
    ) {
        this.letterId = model.letterId
        this.formId = model.formId
        when (formId) {
            Const.LetterType.KEY_INTERNAL -> rdbInternal.isChecked = true
            Const.LetterType.KEY_EXPORTED -> rdbExported.isChecked = true
            Const.LetterType.KEY_IMPORTED -> rdbImported.isChecked = true
        }
        secretariatId = model.secretariatId
        spSecretariat.text = model.secretariatName

        edtBody.setText(model.letterBody)
        edtSubject.setText(model.letterSubject)

        spAccessType.text = model.atName
        atId = model.atId

        spPriority.text = model.casePriorityName
        priorityId = model.casePriorityId

        spDocumentMethodType.text = model.dtName
        documentMethodType = model.dtId

        when (formId) {
            Const.LetterType.KEY_IMPORTED -> {
                spLetterSender.text = model.customerTitle
                edtLetterOtherSender.setText(model.otherCustomer)
                edtImportedLetterNo.setText(model.inLetterNo)
                dvImportedLetter.date = model.inLetterDate ?: viewModel.currentDate
            }
            Const.LetterType.KEY_EXPORTED -> {
                spLetterReceiver.text = model.customerTitle
                edtLetterOtherReceiver.setText(model.otherCustomer)
            }
        }
    }

    private fun getDocumentMethodType() {
        if (listDocumentMethodType.isEmpty()) {

            if (letterStatus == LetterStatus.EXPORTED)
                spDocumentMethodType.loading(true)
            else if (letterStatus == LetterStatus.IMPORTED)
                spReceivedType.loading(true)

            viewModel.getDocumentSendReceiveMethod(
                CardboardDocumentSendReceiveMethodRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = documentSendReceiveMethodJson()
                }
            )

        } else
            showDocumentMethodType()
    }

    private fun showDocumentMethodType() {
        val activity = activity ?: return
        when (letterStatus) {
            LetterStatus.EXPORTED -> spDocumentMethodType.loading(false)
            else -> spReceivedType.loading(false)
        }

        if (listDocumentMethodType.isNotEmpty())
            ChooseDialog(listDocumentMethodType) {
                when (letterStatus) {
                    LetterStatus.EXPORTED -> spDocumentMethodType.text = it.title
                    else -> spReceivedType.text = it.title
                }

                documentMethodType = it.id
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getUserList() {
        userDialogStatus = DialogStatus.CLICKED
        when (userStatus) {
            UserStatus.SENDER -> {
                spLetterSender.loading(true)
            }
            else -> {spLetterReceiver.loading(true)}
        }
        viewModel.getUserList(
            CardboardUserDataRequest().also { request ->
                request.userId = viewModel.userId
                request.registerUserId = viewModel.userId
                request.typeOperation = 105
                request.pageNumber = 1
                request.financialYearId = viewModel.financialYear
                request.jsonParameters = letterInformationJson(formId ?: 0, "")
            }
        )
    }


    private fun getPriority() {
        if (listPriority.isEmpty()) {
            spPriority.loading(false)
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
                priorityId = it.id
                spPriority.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getAccessTypeList() {
        if (listAccessType.isEmpty()) {
            spAccessType.loading(true)
            viewModel.getAccessTypeList(
                CardboardAccessTypeListRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 15
                }
            )
        } else
            showAccessTypeList()
    }

    private fun showAccessTypeList() {
        val activity = activity ?: return
        spAccessType.loading(false)

        if (listAccessType.isNotEmpty())
            ChooseDialog(listAccessType) {
                atId = it.id
                spAccessType.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getSecretariat() {
        if (listSecretariat.isEmpty()) {
            spSecretariat.loading(true)
            viewModel.getSecretariatList(
                CardboardSecretariatRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 11
                }
            )
        } else
            showSecretariatList()
    }

    private fun showSecretariatList() {
        val activity = activity ?: return
        spSecretariat.loading(false)

        if (listSecretariat.isNotEmpty())
            spSecretariat.showData(listSecretariat, activity) {
                secretariatId = it.id
            }
    }

    private fun setChangeLetterStatus() {
        when (letterStatus) {
            LetterStatus.IMPORTED -> {
                layoutUserProfile.isVisible = false
                layoutReceiver.isVisible = true
                dvImportedLetter.date = viewModel.currentDate
            }
            LetterStatus.EXPORTED -> {
                layoutUserProfile.isVisible = true
                layoutReceiver.isVisible = false
            }
            LetterStatus.INTERNAL -> {
                layoutUserProfile.isVisible = false
                layoutReceiver.isVisible = false
            }
        }
    }

    private val isValid: Boolean
        get() {
            return (secretariatId != -1L
                    && formId != -1
                    && atId != -1L
                    && priorityId != -1L
                    && !edtSubject.text.isNullOrEmpty()
                    && !edtBody.text.isNullOrEmpty())
                    &&
                    (when (letterStatus) {
                        LetterStatus.IMPORTED -> {
                            !edtImportedLetterNo.text.isNullOrEmpty()
                                    && documentMethodType != -1L
                                    && customerId != -1L
                        }
                        LetterStatus.EXPORTED -> {
                            documentMethodType != -1L
                                    && customerId != -1L
                        }
                        else -> true
                    })
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

    private fun waitLoading(visible: Boolean) {
        loading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return
        viewModel.error.observe(viewLifecycleOwner,{
            layoutParent.children.forEach { child ->
                if(child is Spinner)
                    child.loading(false)
            }
            if (prcDone.isVisible)
                onStatus(Status.FAILURE)
            waitLoading(false)
            activity.showError(it?.message)
        })
    }

    enum class UserStatus {
        RECEIVER,
        SENDER
    }

    enum class LetterStatus {
        IMPORTED,
        EXPORTED,
        INTERNAL
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterInformationFragment::class.java.simpleName}"
    }
}