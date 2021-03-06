package com.fullsekurity.deptofeducation.utils

import com.fullsekurity.deptofeducation.repository.storage.SchoolField

object Constants {

    const val EDUC_DEPT_ARRAY_DATA_TAG = "educ dept"
    const val ROOT_FRAGMENT_TAG = "root fragment"
    val EDUC_DEPT_LIST_CLASS_TYPE = (ArrayList<SchoolField>()).javaClass
    const val SCHOOLS_DATA_BASE_URL = "https://api.data.gov/ed/collegescorecard/v1/"
    const val PAGE_NUMBER = "page"
    const val EDUC_DEPT_KEY = "api_key"
    const val EDUC_DEPT_FIELDS_KEY = "fields"
    const val EDUC_DEPT_SORT_KEY = "sort"
    const val EDUC_DEPT_SORT_VALUE = "2018.academics.program_percentage.computer:desc"
    const val EDUC_DEPT_FIELDS = "id,school.name,school.city,school.state,school.zip,school.school_url,2018.admissions.sat_scores.average.by_ope_id,2018.academics.program_percentage.computer,2018.admissions.admission_rate.by_ope_id"
    const val EDUC_DEPT_API_KEY = "a32gPmkzRVFctaponTspoNYYqSYkIrZCcd1dkbgx"
    const val STANDARD_LEFT_AND_RIGHT_MARGIN = 20f
    const val STANDARD_EDIT_TEXT_SMALL_MARGIN = 10f
    const val STANDARD_EDIT_TEXT_HEIGHT = 60f
    const val STANDARD_GRID_EDIT_TEXT_HEIGHT = 60f
    const val STANDARD_BUTTON_HEIGHT = 50f
    const val STANDARD_GRID_HEIGHT = 120f
    const val EDIT_TEXT_TO_BUTTON_RATIO = 3  // 3:1
    const val SCHOOLS_DATA_TITLE = "Dept of Education Schools Result"

}