package ir.nik.cardboard.view.fastrefer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.ChooseDialog
import ir.awlrhm.modules.view.CustomChooseDialog
import ir.nik.cardboard.data.network.model.request.*
import ir.nik.cardboard.data.network.model.response.FastReferResponse
import ir.nik.cardboard.data.network.model.response.UserListResponse
import ir.nik.cardboard.utils.*
import ir.nik.cardboard.view.base.BaseFragment
import kotlinx.android.synthetic.main.contain_add_fast_refer.*
import kotlinx.android.synthetic.main.fragment_add_fast_refer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AddFastReferFragment() : BaseFragment() {

    constructor(model: FastReferResponse.Result) : this() {
        this.model = model
    }

    private val viewModel by viewModel<FastReferViewModel>()

    private var model: FastReferResponse.Result? = null
    private var listReferenceAction: MutableList<ItemModel> = mutableListOf()
    private var listReferNature: MutableList<ItemModel> = mutableListOf()
    private var listReferType: MutableList<ItemModel> = mutableListOf()
    private var listReceiver: MutableList<DynamicModel<UserListResponse.Result>> = mutableListOf()

    private var deliveryId: Long = -1
    private var referNatureId: Long = -1
    private var referTypeId: Long = -1
    private var receiverId: Long = -1
    private var _postIdReceiver: Long = -1
    private var _ufrId: Long = 0

    private var isOnEditMode: Boolean = false

    override fun setup() {
        txtTitle.text = getString(R.string.add_fast_refer)

        model?.let { model ->
            isOnEditMode = true
            deliveryId = model.wfsDtId ?: -1
            referNatureId = model.wfsNrId ?: -1
            referTypeId = model.wfsRtId ?: -1
            receiverId = model.userIdReceiver ?: -1
            _postIdReceiver = model.postIdReceiver ?: -1
            _ufrId = model.ufrId ?: -1

            txtReferenceAction.text = model.wfsDtName
            txtReferNature.text = model.wfsNrName
            txtReferType.text = model.wfsRtName
            txtReceiver.text = model.userReceiverFullName
            edtDescription.setText(model.description)
            edtTitle.setText(model.title)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_fast_refer, container, false)
    }

    override fun handleObservers() {
        viewModel.referenceActionResponse.observe(viewLifecycleOwner, { response ->
            listReferenceAction = mutableListOf<ItemModel>().apply {
                response.result?.forEach { result ->
                    val id = result.valueMember ?: -1
                    val name = result.textMember ?: ""
                    if (id != -1L)
                        add(ItemModel(id, name))
                }
            }
            showReferenceActionList()
        })
        viewModel.referNatureResponse.observe(viewLifecycleOwner, { response ->
            listReferNature = mutableListOf<ItemModel>().apply {
                response.result?.forEach { result ->
                    val id = result.baseId ?: -1
                    val name = result.baseName ?: ""
                    if (id != -1L)
                        add(ItemModel(id, name))
                }
            }
            showReferNature()
        })
        viewModel.referTypeResponse.observe(viewLifecycleOwner, { response ->
            listReferType = mutableListOf<ItemModel>().apply {
                response.result?.forEach { result ->
                    val id = result.valueMember ?: -1
                    val name = result.textMember ?: ""
                    if (id != -1L)
                        add(ItemModel(id, name))
                }
            }
            showReferType()
        })
        viewModel.userListResponse.observe(viewLifecycleOwner, {
            it.result?.forEach { model ->
                listReceiver.add(
                    DynamicModel(model.customerFullTitle ?: "", model)
                )
            }
            showReceivers()
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            if (it.result != 0L) {
                activity?.successOperation(it.message)
                onStatus(Status.SUCCESS)

            } else {
                activity?.failureOperation(it.message)
                onStatus(Status.FAILURE)
            }
        })
    }

    //------------------------------------------
    private fun getReferenceActionList() {
        if (listReferenceAction.isEmpty()) {
            onStatus(Status.DELIVERY_LOADING_VISIBLE)
            viewModel.getReferenceActionList(
                ReferenceActionRequest().also { request ->
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
        onStatus(Status.DELIVERY_LOADING_GONE)

        if (listReferenceAction.size != 0)
            ChooseDialog(listReferenceAction) {
                deliveryId = it.id
                txtReferenceAction.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }


    //-----------------------------------------------
    private fun showReferNature() {
        val activity = activity ?: return
        onStatus(Status.REFER_NATURE_LOADING_GONE)

        if (listReferNature.size != 0)
            ChooseDialog(listReferNature) {
                referNatureId = it.id
                txtReferNature.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getReferNature() {
        if (listReferNature.isEmpty()) {
            onStatus(Status.REFER_NATURE_LOADING_VISIBLE)
            viewModel.getReferNature(
                BaseTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = baseTypeJson(Const.BaseType.REFER_NATURE)
                }
            )
        } else
            showReferNature()
    }

    //----------------------------------------------
    private fun getReceivers(
        utIds: Long = 0,  //0 for all receiver - id and search for specific user
        search: String = ""
    ) {
        if (listReceiver.isEmpty()) {
            onStatus(Status.RECEIVER_LOADING_VISIBLE)
            viewModel.getUserList(
                UserDataRequest().also { request ->
                    request.userId = viewModel.userId
                    request.registerUserId = viewModel.userId
                    request.pageNumber = 1
                    request.typeOperation = 101
                    request.financialYearId = viewModel.financialYear
                    request.jsonParameters = userListJson(utIds, search)
                }
            )
        } else
            showReceivers()
    }

    private fun showReceivers() {
        val activity = activity ?: return
        onStatus(Status.RECEIVER_LOADING_GONE)

        if (listReceiver.isNotEmpty())
            CustomChooseDialog.Builder<UserListResponse.Result>()
                .source(listReceiver) {
                    txtReceiver.text = it.title
                    receiverId = it.dynamic.userId ?: 0
                    _postIdReceiver = it.dynamic.postId ?: 0
                }
                .build()
                .show(activity.supportFragmentManager, ChooseDialog.TAG)

    }

    //-----------------------------------------------
    private fun showReferType() {
        val activity = activity ?: return
        onStatus(Status.REFER_TYPE_LOADING_GONE)

        if (listReferType.size != 0)
            ChooseDialog(listReferType) {
                referTypeId = it.id
                txtReferType.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getReferType() {
        if (listReferType.isEmpty()) {
            onStatus(Status.REFER_TYPE_LOADING_VISIBLE)
            viewModel.getReferTypeList(
                ReferenceTypeRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYear
                    request.typeOperation = 10
                    request.jsonParameters = referTypeJson()
                }
            )
        } else
            showReferType()
    }

    override fun handleOnClickListeners() {
        layoutReceiver.setOnClickListener {
            getReceivers()
        }
        layoutReferNature.setOnClickListener {
            getReferNature()
        }
        layoutReferType.setOnClickListener {
            getReferType()
        }
        layoutReferenceAction.setOnClickListener {
            getReferenceActionList()
        }
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                onStatus(Status.LOADING)
                if (isOnEditMode)
                    viewModel.updateUserFastRefer(request)
                else
                    viewModel.insertUserFastRefer(request)
            } else {
                onStatus(Status.FAILURE)
                activity?.yToast(
                    getString(R.string.fill_all_blanks),
                    MessageStatus.ERROR
                )
            }
        }
    }


    private val request: PostFastReferRequest
        get() {
            return PostFastReferRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.postIdReceiver = _postIdReceiver
                request.userIdReceiver = receiverId
                request.ufrId = _ufrId
                request.userId_fk = viewModel.userId
                request.wfsRtId = referTypeId
                request.wfsDtId = deliveryId
                request.wfsNrId = referNatureId
                request.ufrTitle = edtTitle.text.toString()
                request.wfsCrReply = edtDescription.text.toString()
                request.registerDate = viewModel.currentDate
            }
        }
    private val isValid: Boolean
        get() {
            return referTypeId != -1L
                    && referNatureId != -1L
                    && deliveryId != -1L
                    && _ufrId != -1L
                    && _postIdReceiver != -1L
                    && edtTitle.text.toString().isNotEmpty()
                    && edtDescription.text.toString().isNotEmpty()
        }

    private fun onStatus(status: Status) =
        when (status) {
            Status.ALL_LOADING_GONE -> {
                prcReferenceAction.isVisible = false
                prcReferNature.isVisible = false
                prcReferType.isVisible = false
                prcReceiver.isVisible = false
                prcDone.isVisible = false
                btnDone.isVisible = true
                prcReceiver.isVisible = false
            }
            Status.DELIVERY_LOADING_VISIBLE -> {
                prcReferenceAction.isVisible = true
            }
            Status.DELIVERY_LOADING_GONE -> {
                prcReferenceAction.isVisible = false
            }
            Status.REFER_NATURE_LOADING_GONE -> {
                prcReferNature.isVisible = false
            }
            Status.REFER_NATURE_LOADING_VISIBLE -> {
                prcReferNature.isVisible = true
            }
            Status.REFER_TYPE_LOADING_GONE -> {
                prcReferType.isVisible = false
            }
            Status.REFER_TYPE_LOADING_VISIBLE -> {
                prcReferType.isVisible = true
            }
            Status.RECEIVER_LOADING_GONE -> {
                prcReceiver.isVisible = false
            }
            Status.RECEIVER_LOADING_VISIBLE -> {
                prcReceiver.isVisible = true
            }
            Status.LOADING -> {
                btnDone.isVisible = false
                prcDone.isVisible = true
            }
            Status.SUCCESS -> {
                btnDone.isVisible = true
                prcDone.isVisible = false
                activity?.onBackPressed()
            }
            Status.FAILURE -> {
                btnDone.isVisible = true
                prcDone.isVisible = false
            }
        }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            onStatus(Status.ALL_LOADING_GONE)
            activity?.showError(it?.message)
        })
    }

    enum class Status {
        ALL_LOADING_GONE,
        DELIVERY_LOADING_VISIBLE,
        DELIVERY_LOADING_GONE,
        REFER_NATURE_LOADING_VISIBLE,
        REFER_NATURE_LOADING_GONE,
        REFER_TYPE_LOADING_VISIBLE,
        REFER_TYPE_LOADING_GONE,
        RECEIVER_LOADING_VISIBLE,
        RECEIVER_LOADING_GONE,
        LOADING,
        SUCCESS,
        FAILURE
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddFastReferFragment::class.java.simpleName}"
    }
}