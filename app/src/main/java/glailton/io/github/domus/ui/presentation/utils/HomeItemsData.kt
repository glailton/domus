package glailton.io.github.domus.ui.presentation.utils

import glailton.io.github.domus.R
import glailton.io.github.domus.core.models.HomeItem

object HomeItemsData {
    val homeItems = listOf(
        HomeItem(R.string.checklist, R.string.checklist_description, R.drawable.ic_checklist, 0),
        HomeItem(R.string.receipts, R.string.receipts_description, R.drawable.ic_receipt, 1),
        HomeItem(R.string.schedules, R.string.schedules_description, R.drawable.ic_schedule, 2)
    )
}