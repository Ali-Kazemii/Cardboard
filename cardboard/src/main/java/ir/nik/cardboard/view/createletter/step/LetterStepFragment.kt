package ir.nik.cardboard.view.createletter.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nik.cardboard.R
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.showFlashbar
import ir.nik.cardboard.data.network.model.response.DraftLetterResponse
import ir.nik.cardboard.utils.Const
import ir.nik.cardboard.view.base.BaseFragment
import ir.nik.cardboard.view.createletter.CreateLetterViewModel
import kotlinx.android.synthetic.main.fragment_letter_step.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LetterStepFragment(
    private val callback: (String) -> Unit
) : BaseFragment() {

    constructor(
        model: DraftLetterResponse.Result?,
        callback: (String) -> Unit
    ) : this(callback) {
        this.model = model
    }

    private val viewModel by viewModel<CreateLetterViewModel>()

    private var model: DraftLetterResponse.Result? = null
    private var letterId: Long = 0
    private var listStep: List<StepModel> = mutableListOf()

    override fun setup() {
        rclStep.layoutManager(LinearLayoutManager(activity))

        listStep = viewModel.getCreateLetterSteps()
        model?.let { model ->
            this.letterId = model.letterId ?: 0

            listStep[0].description =
                model.letterInfoDescription ?: getString(R.string.not_complete)
            listStep[0].status = model.letterInformationStatus ?: 0

            listStep[1].description =
                model.letterAttachmentDescription ?: getString(R.string.not_complete)
            listStep[1].status = model.letterAttachmentStatus ?: 0

            listStep[2].description =
                model.letterReferralDescription ?: getString(R.string.not_complete)
            listStep[2].status = model.letterReferralStatus ?: 0

            listStep[3].description =
                model.letterLinkDescription ?: getString(R.string.not_complete)
            listStep[3].status = model.letterLinkStatus ?: 0

            listStep[4].description =
                model.letterExtraInformationDescription ?: getString(R.string.not_complete)
            listStep[4].status = model.letterExtraInformationStatus ?: 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_letter_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rclStep.view?.adapter = Adapter(listStep) { step ->
            callback.invoke(step)
        }
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener { activity?.onBackPressed() }
    }

    override fun handleObservers() {
        viewModel.responseId.observe(viewLifecycleOwner, { response ->
            rclStep.actionLoading = false
            response.result?.let {
                if (it != 0L) {
                    activity?.showFlashbar(
                        getString(R.string.success),
                        response.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )

                } else {
                    activity?.failureOperation(response.message)
                }
            } ?: kotlin.run {
                activity?.failureOperation(response.message)
            }
        })
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            rclStep.actionLoading = false
            activity?.showError(it?.message)
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LetterStepFragment::class.java.simpleName}"
    }
}