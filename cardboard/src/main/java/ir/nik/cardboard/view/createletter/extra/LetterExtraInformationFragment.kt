package ir.nik.cardboard.view.createletter.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.failedData
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.ChooseDialog
import ir.awlrhm.modules.view.Spinner
import ir.awlrhm.modules.view.searchablePagingDialog.DialogStatus
import ir.awlrhm.modules.view.searchablePagingDialog.SearchablePagingDialog
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.CardboardCommercialDocumentByCustomerIdResponse
import ir.nik.cardboard.data.network.model.response.CardboardCustomerAccountByCustomerIdResponse
import ir.nik.cardboard.data.network.model.response.CardboardProjectSpacialListResponse
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.view.base.CardboardBaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.contain_letter_further_information_cardboard.*
import kotlinx.android.synthetic.main.fragment_letter_extra_information_cardboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterExtraInformationFragment(
    private val letterId: Long,
    private val customerId: Long
) : CardboardBaseFragment(), OnStatusListener {

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var listContractNumber: MutableList<ItemModel> = mutableListOf()

    private lateinit var projectDialog: SearchablePagingDialog<CardboardProjectSpacialListResponse.Result>
    private var projectDialogStatus = DialogStatus.CLICKED

    private lateinit var commercialDialog: SearchablePagingDialog<CardboardCommercialDocumentByCustomerIdResponse.Result>
    private var commercialDialogStatus = DialogStatus.CLICKED

    private lateinit var customerAccountDialog: SearchablePagingDialog<CardboardCustomerAccountByCustomerIdResponse.Result>
    private var customerAccountDialogStatus = DialogStatus.CLICKED

    private var contractId: Long = 0
    private var customerAccount: Long = 0
    private var commercialId: Long = 0
    private var projectId: Long = 0

    override fun setup() {
        projectDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<CardboardProjectSpacialListResponse.Result> {
                override fun onChoose(model: DynamicModel<CardboardProjectSpacialListResponse.Result>) {
                    spProject.text = model.title
                    projectId = model.dynamic.projectId ?: 0
                }

                override fun onDismiss() {
                    projectDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getProject(pageNumber, search)
                }
            }
        )

        commercialDialog = SearchablePagingDialog(
            object :
                SearchablePagingDialog.OnActionListener<CardboardCommercialDocumentByCustomerIdResponse.Result> {
                override fun onChoose(model: DynamicModel<CardboardCommercialDocumentByCustomerIdResponse.Result>) {
                    spCommercialId.text = model.title
                    commercialId = model.dynamic.cdId ?: 0
                }

                override fun onDismiss() {
                    commercialDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(
                    pageNumber: Int,
                    search: String
                ) {
                    viewModel.getCommercialDocumentByCustomerId(
                        CardboardCommercialDocumentByCustomerIdRequest().also { request ->
                            request.userId = viewModel.userId
                            request.financialYearId = viewModel.financialYear
                            request.pageNumber = pageNumber
                            request.typeOperation = 14
                            request.jsonParameters =
                                commercialDocumentByCustomerId(
                                    customerId = customerId,
                                    search = search
                                )
                        }
                    )
                }
            }
        )

        customerAccountDialog = SearchablePagingDialog(
            object :
                SearchablePagingDialog.OnActionListener<CardboardCustomerAccountByCustomerIdResponse.Result> {
                override fun onChoose(model: DynamicModel<CardboardCustomerAccountByCustomerIdResponse.Result>) {
                    spCustomerAccount.text = model.title
                    customerAccount = model.dynamic.caId ?: 0
                }

                override fun onDismiss() {
                    customerAccountDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getCustomerAccount(pageNumber, search)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_extra_information_cardboard, container, false)
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.contractByCustomerIdResponse.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                if (list.isEmpty())
                    spContractNo.failedData()
                else {
                    list.forEach { model ->
                        listContractNumber.apply {
                            add(
                                ItemModel(model.contractId ?: 0, model.contractTitle ?: "")
                            )
                        }
                    }
                    showContractNumber()
                }
            } ?: kotlin.run {
                spContractNo.failedData()
            }
        })
        viewModel.projectSpacialResponse.observe(viewLifecycleOwner, { response ->
            spProject.loading(false)
            if (projectDialogStatus != DialogStatus.CLICKED)
                return@observe

            response.result?.let { list ->
                val listProject = mutableListOf<DynamicModel<CardboardProjectSpacialListResponse.Result>>()
                listProject.also { listModel ->
                    list.forEach { item ->
                        listModel.add(
                            DynamicModel(item.projectName ?: "", item)
                        )
                    }
                }
                if (listProject.isNotEmpty()) {
                    projectDialog.setSource(response.resultCount ?: 0, listProject)
                    if (!projectDialog.isVisible)
                        projectDialog.show(
                            activity.supportFragmentManager,
                            SearchablePagingDialog.TAG
                        )
                } else if (projectDialog.isVisible)
                    projectDialog.showNoData()
                else
                    spProject.failedData()

            } ?: kotlin.run {
                spProject.failedData()
            }
        })
        viewModel.commercialDocumentByCustomerIdResponse.observe(
            viewLifecycleOwner,
            { response ->
                spCommercialId.loading(false)
                if (commercialDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    val listCommercialId =
                        mutableListOf<DynamicModel<CardboardCommercialDocumentByCustomerIdResponse.Result>>()
                    listCommercialId.also { listModel ->
                        list.forEach { item ->
                            listModel.add(
                                DynamicModel(item.cdTitle ?: "", item)
                            )
                        }
                    }
                    if (listCommercialId.isNotEmpty()) {
                        commercialDialog.setSource(response.resultCount ?: 0, listCommercialId)
                        if (!commercialDialog.isVisible)
                            commercialDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )
                    } else if (commercialDialog.isVisible)
                        commercialDialog.showNoData()
                    else
                        spCommercialId.failedData()

                } ?: kotlin.run {
                    spCommercialId.failedData()
                }
            })

        viewModel.customerAccountByCustomerIdResponse.observe(
            viewLifecycleOwner,
            { response ->
                spCustomerAccount.loading(false)
                if (customerAccountDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->

                    val listCustomerAccount =
                        mutableListOf<DynamicModel<CardboardCustomerAccountByCustomerIdResponse.Result>>()
                    listCustomerAccount.also { listModel ->
                        list.forEach { item ->
                            listModel.add(
                                DynamicModel(item.subject ?: "", item)
                            )
                        }
                    }
                    if (listCustomerAccount.isNotEmpty()) {
                        customerAccountDialog.setSource(
                            response.resultCount ?: 0,
                            listCustomerAccount
                        )
                        if (!customerAccountDialog.isVisible)
                            customerAccountDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )
                    } else if (customerAccountDialog.isVisible)
                        customerAccountDialog.showNoData()
                    else
                        spCustomerAccount.failedData()

                } ?: kotlin.run {
                    spCustomerAccount.failedData()
                }
            })
        viewModel.responseId.observe(viewLifecycleOwner, {
            if (it.result != 0L) {
                onStatus(Status.SUCCESS)
                ActionDialog.Builder()
                    .setTitle(getString(R.string.success))
                    .setDescription(it.message ?: getString(R.string.success_operation))
                    .setPositive(getString(R.string.ok)) {
                        activity.onBackPressed()
                    }
                    .setCancelable(false)
                    .build()
                    .show(activity.supportFragmentManager, ActionDialog.TAG)

            } else {
                onStatus(Status.FAILURE)
                activity.showError(it.message)
            }
        })
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        spContractNo.setOnClickListener {
            getContractNumber()
        }
        spCommercialId.setOnClickListener {
            getCommercialId()
        }
        spCustomerAccount.setOnClickListener {
            customerAccountDialogStatus = DialogStatus.CLICKED
            spCustomerAccount.loading(true)
            getCustomerAccount(1)
        }
        spProject.setOnClickListener {
            spProject.loading(true)
            projectDialogStatus = DialogStatus.CLICKED
            getProject(1)
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnDone.setOnClickListener {
            onStatus(Status.LOADING)
            viewModel.postLetterExtraInformation(
                CardboardPostLetterExtraInformationRequest().also { request ->
                    request.letterPropertyJson =
                        if (layoutKeyWords.childCount != 0)
                            getLetterExtraInformationJson(this.letterId, getKeyWords())
                        else null
                    request.letterId = letterId
                    request.caId = customerAccount
                    request.cdId = commercialId
                    request.projectId = projectId
                    request.contractId = contractId
                    request.financialYearId = viewModel.financialYear
                    request.registerDate = viewModel.currentDate
                }
            )
        }
        btnAddKeyWord.setOnClickListener {
            if (edtKeyWord.text.toString().isNotEmpty())
                createKeyWord(edtKeyWord.text.toString())
        }
    }

    private fun getKeyWords(): List<String> {
        val list: MutableList<String> = mutableListOf()
        for (i in 0..layoutKeyWords.childCount)
            list.add((layoutKeyWords.getChildAt(0) as Chip).text.toString())
        return list
    }

    private fun createKeyWord(keyWord: String) {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.item_chip_cardboard, layoutKeyWords, false) as Chip
        view.text = keyWord
        view.setOnClickListener { layoutKeyWords.removeView(view) }
        view.setOnCloseIconClickListener { layoutKeyWords.removeView(view) }
        layoutKeyWords.addView(view)
        edtKeyWord.setText("")
    }

    private fun getProject(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getProjectSpacialList(
            CardboardProjectSpacialListRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = projectSpacialListJson(
                    customerId = customerId,
                    search = search
                )
            }
        )
    }

    private fun getCustomerAccount(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getCustomerAccountByCustomerId(
            CardboardCustomerAccountByCustomerIdRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = customerAccountByCustomerIdJson(
                    search = search,
                    customerId = customerId
                )
            }
        )
    }

    private fun getCommercialId() {
        spCommercialId.loading(true)
        commercialDialogStatus = DialogStatus.CLICKED
        viewModel.getCommercialDocumentByCustomerId(
            CardboardCommercialDocumentByCustomerIdRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.pageNumber = 1
                request.typeOperation = 14
                request.jsonParameters =
                    commercialDocumentByCustomerId(
                        customerId = customerId,
                        search = ""
                    )
            }
        )
    }

    private fun getContractNumber() {
        if (listContractNumber.isEmpty()) {
            spContractNo.loading(true)
            viewModel.getContractByCustomerId(
                CardboardContractByCustomerIdRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 12
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = contractByCustomerIdJson(customerId, "")
                }
            )
        } else
            showContractNumber()
    }

    private fun showContractNumber() {
        val activity = activity ?: return

        spContractNo.loading(false)
        if (listContractNumber.isNotEmpty())
            ChooseDialog(listContractNumber) {
                spContractNo.text = it.title
                contractId = it.id
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
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
                if (child is Spinner)
                    child.loading(false)
            }
            activity?.showError(it?.message)
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterExtraInformationFragment::class.java.simpleName}"
    }
}