package com.example.validationpractice.utlis

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.exobe.modelClass.BrandList
import com.exobe.R


object FormValidation : Activity() {
    val emailPattern =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    var MobilePattern = "[0-9]{10}"
    val Password = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
    val Name = "^[A-Za-z]+$"
    val UserName = "^(?=[a-zA-Z0-9._]{8,20}\$)(?!.*[_.]{2})[^_.].*[^_.]\$"
    var any_Number = "[0-9]"
    var link =
        "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})"
    var otp_regex = "^[0-9]{6}\$"
    var priceRegex = "^\\d{0,8}(\\.\\d{1,4})?\$"


    fun forgetPassword(
        PhoneNumberET: EditText,
        PhoneNumberTV: TextView,
        PhoneNumberLL: LinearLayout

    ): Boolean {
        PhoneNumberTV.text = ""

        PhoneNumberLL.setBackgroundResource(R.drawable.backgroundsearch)

        if (PhoneNumberET.text.isEmpty()) {
            PhoneNumberET.requestFocus()
            PhoneNumberTV.text = "*Please enter your phone number."
            PhoneNumberLL.setBackgroundResource(R.drawable.input_error)

        } else if (!(PhoneNumberET.text.matches(Regex(MobilePattern)))) {
            PhoneNumberET.requestFocus()
            PhoneNumberTV.text = "*Please enter valid phone number."
            PhoneNumberLL.setBackgroundResource(R.drawable.input_error)

        } else {
            PhoneNumberTV.text = ""
            PhoneNumberLL.setBackgroundResource(R.drawable.backgroundsearch)

            return true
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    fun AddDeals(
        image_Relative: RelativeLayout,
        USER_IMAGE_UPLOADED: Boolean,
        Image_tv: TextView,
        Category: TextView,
        Category_TV: TextView,
        SubCategory: TextView,
        SubCategory_TV: TextView,
        AmmountDeals: EditText,
        Ammount_TV: TextView,
        startdate: RelativeLayout,
        textstartdate: TextView,
        Startdate_TV: TextView,
        end_date: RelativeLayout,
        textenddate: TextView,
        Enddate_TV: TextView,
        selectService: TextView,
        selectService_tv: TextView,
        start_time: TextView,
        end_time: TextView
    ) {
        if (selectService.text.isEmpty()) {
            SubCategory_TV.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            selectService_tv.text = "*Please select service."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.input_error)

        }else if (Category.text.isEmpty()) {
            Category.requestFocus()
            Image_tv.text = ""
            Category_TV.text = "*Please select category."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.input_error)
        } else if (SubCategory.text.isEmpty()) {
            SubCategory_TV.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = "*Please select sub category."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.input_error)

        } else if (AmmountDeals.text.isEmpty()) {
            AmmountDeals.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            selectService_tv.text = ""
            Ammount_TV.text = "*Please enter discount."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.input_error)

        } else if (AmmountDeals.text.trimStart().startsWith("0")) {
            AmmountDeals.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            selectService_tv.text = ""
            Ammount_TV.text = "*Please enter valid deal percentage."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.input_error)


        }  else if (textstartdate.text.isEmpty()) {
            textstartdate.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            Ammount_TV.text = ""
            selectService_tv.text = ""
            Startdate_TV.text = "*Please select start date and start time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.input_error)
        } else if (start_time.text.isEmpty()) {
            textstartdate.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            Ammount_TV.text = ""
            selectService_tv.text = ""
            Startdate_TV.text = "*Please select start date and start time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.input_error)
        } else if (textenddate.text.isEmpty()) {
            textenddate.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            Ammount_TV.text = ""
            Startdate_TV.text = ""
            selectService_tv.text = ""
            Enddate_TV.text = "*Please select end date and end time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            end_date.setBackgroundResource(R.drawable.input_error)
        } else if (end_time.text.isEmpty()) {
            textenddate.requestFocus()
            Category_TV.text = ""
            SubCategory_TV.text = ""
            Ammount_TV.text = ""
            Startdate_TV.text = ""
            selectService_tv.text = ""
            Enddate_TV.text = "*Please select end date and end time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            end_date.setBackgroundResource(R.drawable.input_error)
        } else {
            Category_TV.text = ""
            SubCategory_TV.text = ""
            Ammount_TV.text = ""
            Startdate_TV.text = ""
            Enddate_TV.text = ""
            selectService_tv.text = ""
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            Category.setBackgroundResource(R.drawable.backgroundsearch)
            SubCategory.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)
            selectService.setBackgroundResource(R.drawable.backgroundsearch)
            end_date.setBackgroundResource(R.drawable.backgroundsearch)

        }

    }

    @SuppressLint("SetTextI18n")
    fun editDeals(
        image_Relative: RelativeLayout,
        USER_IMAGE_UPLOADED: Boolean,
        Image_tv: TextView,
        AmmountDeals: EditText,
        Ammount_TV: TextView,
        startdate: RelativeLayout,
        textstartdate: TextView,
        Startdate_TV: TextView,
        end_date: RelativeLayout,
        textenddate: TextView,
        Enddate_TV: TextView,
        start_time: TextView,
        end_time: TextView,

        ) {
        if (USER_IMAGE_UPLOADED == false) {
            Image_tv.text = "*Please select image."
            image_Relative.setBackgroundResource(R.drawable.input_error)

        } else if (AmmountDeals.text.isEmpty()) {
            AmmountDeals.requestFocus()

            Ammount_TV.text = "*Please enter discount."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)
            AmmountDeals.setBackgroundResource(R.drawable.input_error)

        } else if (AmmountDeals.text.trimStart().startsWith("0")) {
            AmmountDeals.requestFocus()

            Ammount_TV.text = "*Please enter valid deal percentage."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.input_error)


        } else if (textstartdate.text.isEmpty()) {
            textstartdate.requestFocus()

            Ammount_TV.text = ""
            Startdate_TV.text = "*Please select start date and start time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)

            startdate.setBackgroundResource(R.drawable.input_error)
        } else if (start_time.text.isEmpty()) {
            textstartdate.requestFocus()

            Ammount_TV.text = ""
            Startdate_TV.text = "*Please select start date and start time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)

            startdate.setBackgroundResource(R.drawable.input_error)
        } else if (textenddate.text.isEmpty()) {
            textenddate.requestFocus()

            Ammount_TV.text = ""
            Startdate_TV.text = ""

            Enddate_TV.text = "*Please select end date and end time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)

            end_date.setBackgroundResource(R.drawable.input_error)
        } else if (end_time.text.isEmpty()) {
            textenddate.requestFocus()

            Ammount_TV.text = ""
            Startdate_TV.text = ""

            Enddate_TV.text = "*Please select end date and end time."
            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)

            end_date.setBackgroundResource(R.drawable.input_error)
        } else {

            Ammount_TV.text = ""
            Startdate_TV.text = ""
            Enddate_TV.text = ""

            image_Relative.setBackgroundResource(R.drawable.backgroundsearch)

            AmmountDeals.setBackgroundResource(R.drawable.backgroundsearch)
            startdate.setBackgroundResource(R.drawable.backgroundsearch)

            end_date.setBackgroundResource(R.drawable.backgroundsearch)

        }

    }

    @SuppressLint("SetTextI18n")
    fun filldetails(
        FirstName_ET: EditText,
        first_nametv: TextView,
        LastName_ET: EditText,
        lastname_tv: TextView,
        Email_ET: EditText,
        emailaddress_tv: TextView,
        Companyname_ET: EditText,
        company_name_TV: TextView,
        businessRegistrationET: EditText,
        businessregistration_TV: TextView,
        RB_yes: RadioButton,
        RB_no: RadioButton,
        Vat_ET: EditText,
        VAT_TV: TextView,
        values: Boolean = false,
        Monthly_RevenueTextview: TextView,
        Bankname_ET: EditText,
        bank_name_TV: TextView,
        Branchname_ET: EditText,
        branch_name_TV: TextView,
        Branchcode_ET: EditText,
        branch_code_TV: TextView,
        SWIFTcode_ET: EditText,
        swiftcode_TV: TextView,
        accounttype_TV: TextView,
        Accountname_ET: EditText,
        account_name_TV: TextView,
        Accountnumber_ET: EditText,
        account_number_TV: TextView,
        account_type_ll: LinearLayout,
        requestAccountType: String
    ) {
        if (FirstName_ET.text.isEmpty()) {
            first_nametv.text = "*Please enter your first name."
            FirstName_ET.setBackgroundResource(R.drawable.input_error)
        } else if (FirstName_ET.text.length < 2) {
            first_nametv.text = "*Please enter valid first name."
            FirstName_ET.setBackgroundResource(R.drawable.input_error)
        } else if (LastName_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = "*Please enter your lastname."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.input_error)
        } else if (LastName_ET.text.length < 2) {
            first_nametv.text = ""
            lastname_tv.text = "*Please enter valid last name."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Email_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = "*Please enter your email address."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.input_error)
        } else if (!Email_ET.text.matches(Regex(emailPattern))) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = "*Please enter valid email address."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Companyname_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = "*Please enter company name."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.input_error)
        } else if (businessRegistrationET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = "*Please enter business registration number."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.input_error)
        } else if ((RB_yes.isChecked == true && Vat_ET.text.isEmpty())) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = "*Please enter VAT number."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.input_error)

        } else if (values == false) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = "*Please select atleast one option."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (Bankname_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = "*Please enter bank name."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.input_error)

        } else if (Branchname_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = "*Please enter branch name."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Branchcode_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = "*Please enter branch code."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.input_error)
        } else if (SWIFTcode_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = "*Please enter swift code."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.input_error)
        } else if (SWIFTcode_ET.text.toString().length < 8) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = "*Please enter valid swift code."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.input_error)
        } else if (requestAccountType.equals("Select")) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = ""
            accounttype_TV.text = "*Please select account type."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.input_error)
        } else if (Accountname_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = ""
            accounttype_TV.text = ""
            account_name_TV.text = "*Please enter account name."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            Accountname_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Accountnumber_ET.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = ""
            accounttype_TV.text = ""
            account_name_TV.text = ""
            account_number_TV.text = "*Please enter account number."
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            Accountname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Accountnumber_ET.setBackgroundResource(R.drawable.input_error)
        } else {
            first_nametv.text = ""
            lastname_tv.text = ""
            emailaddress_tv.text = ""
            company_name_TV.text = ""
            businessregistration_TV.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            bank_name_TV.text = ""
            branch_name_TV.text = ""
            branch_code_TV.text = ""
            swiftcode_TV.text = ""
            accounttype_TV.text = ""
            account_name_TV.text = ""
            account_number_TV.text = ""
            FirstName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Companyname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            businessRegistrationET.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Bankname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Branchcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            SWIFTcode_ET.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            Accountname_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Accountnumber_ET.setBackgroundResource(R.drawable.backgroundsearch)
        }


    }


    @SuppressLint("SetTextI18n")
    fun ServiceProviderRegistration(

        FirstNameET: EditText,
        FirstNameTV: TextView,
        LastNameET: EditText,
        LastNameTV: TextView,
        EmailET: EditText,
        EmailTV: TextView,
        MobileET: EditText,
        MobileTV: TextView,
        country: LinearLayout,
        Address1ET: EditText,
        Address1TV: TextView,
        CountryET: TextView,
        CountryTV: TextView,
        ZipcodeET: EditText,
        ZipcodeTV: TextView,
        PasswordET: EditText,
        PasswordTV: TextView,
        PaswordLL: LinearLayout,
        ConfirmET: EditText,
        ConfirmTV: TextView,
        ConfirmPaswordLL: LinearLayout,
        Address1eEtLL: LinearLayout,
        isSocial:Boolean
    ) {
        if (FirstNameET.text.isEmpty()) {
            FirstNameTV.text = "*Please enter your first name."
            FirstNameET.setBackgroundResource(R.drawable.input_error)
        } else if (FirstNameET.text.length < 2) {
            FirstNameTV.text = "*Please enter valid first name."
            FirstNameET.setBackgroundResource(R.drawable.input_error)
        } else if (LastNameET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = "*Please enter your lastname."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.input_error)
        } else if (LastNameET.text.length < 2) {
            FirstNameTV.text = ""
            LastNameTV.text = "*Please enter valid last name."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.input_error)
        } else if (EmailET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = "*Please enter your email address."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.input_error)
        } else if (!EmailET.text.matches(Regex(emailPattern))) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = "*Please enter valid email address."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.input_error)
        } else if (MobileET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = "*Please enter your mobile number."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (MobileET.text.length < 9) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = "*Please enter valid mobile number."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (MobileET.text.trimStart().startsWith("0")) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = "*Please enter valid mobile number."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (Address1ET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = "*Please enter your address."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.input_error)
        } else if (CountryET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = "*Please enter Country."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.input_error)
        }
        else if (ZipcodeET.text.isEmpty()) {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = "*Please enter zipcode."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.input_error)
        } else if (ZipcodeET.text.length < 4) {

            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = "*Please enter valid zipcode."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.input_error)
        } else if (PasswordET.text.isEmpty() && !isSocial) {

            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = ""
            PasswordTV.text = "*Please enter password."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (PasswordET.text.toString().length < 6 && !isSocial) {

            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = ""
            PasswordTV.text = "*Password length should be grater than 6."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (ConfirmET.text.isEmpty()  && !isSocial) {

            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = ""
            PasswordTV.text = ""
            ConfirmTV.text = "*Please enter confirm password."
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (!ConfirmET.text.toString().equals(PasswordET.text.toString()) && !isSocial) {

            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = ""
            PasswordTV.text = ""
            ConfirmTV.text = "*Confirm password doesn't matched"
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            //StateET.setBackgroundResource(R.drawable.backgroundsearch)
            ////CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.input_error)
        } else {
            FirstNameTV.text = ""
            LastNameTV.text = ""
            EmailTV.text = ""
            MobileTV.text = ""
            Address1TV.text = ""
            CountryTV.text = ""
            ZipcodeTV.text = ""
            PasswordTV.text = ""
            ConfirmTV.text = ""
            FirstNameET.setBackgroundResource(R.drawable.backgroundsearch)
            LastNameET.setBackgroundResource(R.drawable.backgroundsearch)
            EmailET.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            Address1eEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            CountryET.setBackgroundResource(R.drawable.backgroundsearch)
            ZipcodeET.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
        }
    }

    fun forgotPassword(
        EmailAddress_et: EditText,
        PhoneNumberTV: TextView,
        EmailAddress: LinearLayout
    ) {
        PhoneNumberTV.text = ""
        EmailAddress.setBackgroundResource(R.drawable.backgroundsearch)
        if (EmailAddress_et.text.isEmpty()) {
            PhoneNumberTV.text = "*Please enter email address"
            EmailAddress.setBackgroundResource(R.drawable.input_error)
        } else if (!EmailAddress_et.text.matches(Regex(emailPattern))) {
            PhoneNumberTV.text = "*Please enter valid email address"
            EmailAddress.setBackgroundResource(R.drawable.input_error)
        } else {
            PhoneNumberTV.text = ""

        }
    }

    fun ProfilUpdate(
        First_name_ET: EditText,
        FirstName_TV: TextView,
        Last_name_ET: EditText,
        Last_name_TV: TextView,
        Email_ET: EditText,
        Email_TV: TextView,
        Mobile_ET: EditText,
        Mobile_TV: TextView,
        Address1_ET: EditText,
        Address1_TV: TextView,
        country1: TextView,
        Country: TextView,
//        State_ET: TextView,
//        State_TV: TextView,
//        CityEt: TextView,
//        CityTV: TextView,
        postalcode: EditText,
        errorTvEditPostCode: TextView,
        Address1EtLL: LinearLayout,
        MobileEtLL: LinearLayout,
    ) {
        if (First_name_ET.text.isEmpty()) {
            FirstName_TV.text = "*Please enter your first name."
            First_name_ET.setBackgroundResource(R.drawable.input_error)
        } else if (First_name_ET.text.length < 2) {
            FirstName_TV.text = "*Please enter valid first name."
            First_name_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Last_name_ET.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = "*Please enter your lastname."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Email_ET.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = "*Please enter your email address."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.input_error)
        } else if (!Email_ET.text.matches(Regex(emailPattern))) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = "*Please enter valid email address."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.input_error)
        } else if (Mobile_ET.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = "*Please enter your mobile number."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.input_error)
        } else if (Mobile_ET.text.length < 9) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = "*Please enter valid mobile number."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.input_error)
        } else if (Mobile_ET.text.trimStart().startsWith("0")) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = "*Please enter valid mobile number."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.input_error)
        } else if (Address1_ET.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = ""
            Address1_TV.text = "*Please enter your address line 1."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1EtLL.setBackgroundResource(R.drawable.input_error)
        } else if (country1.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = ""
            Address1_TV.text = ""
            Country.text = "*Please select country."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.input_error)
        } 
//        else if (State_ET.text.isEmpty()) {
//            FirstName_TV.text = ""
//            Last_name_TV.text = ""
//            Email_TV.text = ""
//            Mobile_TV.text = ""
//            Address1_TV.text = ""
//            Country.text = ""
//            State_TV.text = "*Please select state."
//            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
//            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
//            country1.setBackgroundResource(R.drawable.backgroundsearch)
//            State_ET.setBackgroundResource(R.drawable.input_error)
//        } else if (CityEt.text.isEmpty()) {
//            FirstName_TV.text = ""
//            Last_name_TV.text = ""
//            Email_TV.text = ""
//            Mobile_TV.text = ""
//            Address1_TV.text = ""
//            Country.text = ""
//            //state_tv.text = ""
//            CityTV.text = "*Please enter your city."
//            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
//            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
//            country1.setBackgroundResource(R.drawable.backgroundsearch)
//            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            CityEt.setBackgroundResource(R.drawable.input_error)
//        }
        else if (postalcode.text.isEmpty()) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = ""
            Address1_TV.text = ""
            Country.text = ""
            //state_tv.text = ""
            //CityTV.text = ""
            errorTvEditPostCode.text = "*Please enter your zipcode code."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            postalcode.setBackgroundResource(R.drawable.input_error)
        } else if (postalcode.text.length < 4) {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = ""
            Address1_TV.text = ""
            Country.text = ""
            //state_tv.text = ""
            //CityTV.text = ""
            errorTvEditPostCode.text = "*Please enter valid zipcode."
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            postalcode.setBackgroundResource(R.drawable.input_error)
        } else {
            FirstName_TV.text = ""
            Last_name_TV.text = ""
            Email_TV.text = ""
            Mobile_TV.text = ""
            Address1_TV.text = ""
            Country.text = ""
            //state_tv.text = ""
            //CityTV.text = ""
            First_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Last_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            Email_ET.setBackgroundResource(R.drawable.backgroundsearch)
            MobileEtLL.setBackgroundResource(R.drawable.backgroundsearch)
            Address1EtLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            postalcode.setBackgroundResource(R.drawable.backgroundsearch)
        }

    }

    fun LoginRetailerValidations(
        RetailerET: EditText,
        emailTV: TextView,
        PasswordEditText: EditText,
        passwordTv: TextView,
        RetalierLinear: LinearLayout,
        PaswordLL: LinearLayout
    ): Boolean {

        emailTV.text = ""
        RetalierLinear.setBackgroundResource(R.drawable.backgroundsearch)
        passwordTv.text = ""
        PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)

        val retailer = RetailerET.text.toString()
        val password = PasswordEditText.text.toString()


        if (retailer.isEmpty()) {
            RetailerET.requestFocus()
            emailTV.text = "*Please enter retailer id."
            RetalierLinear.setBackgroundResource(R.drawable.input_error)

        } else if (password.isEmpty()) {
            passwordTv.isVisible = true
            PasswordEditText.requestFocus()
            passwordTv.text = "*Please enter your password."
            PaswordLL.setBackgroundResource(R.drawable.input_error)

        } else if (password.length < 8) {
            PasswordEditText.requestFocus()
            passwordTv.text = "*Please enter valid password."
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else {
            emailTV.text = ""
            RetalierLinear.setBackgroundResource(R.drawable.backgroundsearch)
            passwordTv.text = ""
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)

            return true
        }
        return false

    }

    fun LoginValidations(
        phoneET: EditText,
        phoneTV: TextView,
        passwordET: EditText,
        passwordTv: TextView,
        PhoneLinear: LinearLayout,
        PaswordLL: LinearLayout
    ): Boolean {

        phoneTV.text = ""
        PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)
        passwordTv.text = ""
        PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)


        val phone = phoneET.text.toString()
        val password = passwordET.text.toString()

        if (phone.isEmpty()) {
            phoneET.requestFocus()
            phoneTV.text = "*Please enter phone number."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (!isPhone(phone)) {
            phoneET.requestFocus()
            phoneTV.text = "*Please enter valid phone number."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (password.isEmpty()) {
            passwordET.requestFocus()
            passwordTv.isVisible = true
            passwordTv.text = "*Please enter password."
            PaswordLL.setBackgroundResource(R.drawable.input_error)

        } else if (password.length < 8) {
            passwordET.requestFocus()
            passwordTv.text = "*Please enter valid password."
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else {
            phoneTV.text = ""
            PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)
            passwordTv.text = ""
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)

            return true
        }

        return false
    }

    // validations for reset password

    fun ResetPasswordValidations(

        passwordET: EditText,
        passwordTV: TextView,
        confirmPasswordET: EditText,
        confirmPasswordTV: TextView,
        passwordLinear: LinearLayout,
        confrimPasswordLinear: LinearLayout
    ): Boolean {

        ClearFieldsReset(
            passwordTV,
            confirmPasswordTV,
            passwordLinear,
            confrimPasswordLinear
        )

        val password = passwordET.text.toString().trim()
        val confrimPassword = confirmPasswordET.text.toString().trim()

        if (password.isEmpty()) {
            passwordTV.visibility = View.VISIBLE
            passwordTV.setText("*Please enter new password.")
            passwordLinear.setBackgroundResource(R.drawable.input_error)

        } else if (confrimPassword.isEmpty()) {
            confirmPasswordTV.visibility = View.VISIBLE
            confirmPasswordTV.setText("*Please enter confirm password.")
            confrimPasswordLinear.setBackgroundResource(R.drawable.input_error)

        } else if (!confrimPassword.equals(password)) {
            confirmPasswordTV.visibility = View.VISIBLE
            confirmPasswordTV.setText("*Confirm password doesn't matched.")
            confrimPasswordLinear.setBackgroundResource(R.drawable.input_error)

        } else {

            return true
        }
        return false
    }

    fun ClearFieldsReset(


        passwordTV: TextView,
        confirmPasswordTV: TextView,
        passwordLinear: LinearLayout,
        confrimPasswordLinear: LinearLayout
    ) {

        passwordTV.text = ""
        passwordTV.visibility = View.GONE
        passwordLinear.setBackgroundResource(R.drawable.backgroundsearch)

        confirmPasswordTV.visibility = View.GONE
        confirmPasswordTV.text = ""
        confrimPasswordLinear.setBackgroundResource(R.drawable.backgroundsearch)


    }

    fun addaddress(
        et_firstname: EditText,
        tv_firstname: TextView,
        et_lastname: EditText,
        tv_lastname: TextView,
        et_contactnumber: EditText,
        tv_contactnumber: TextView,
        country: LinearLayout,
        EmailEt: EditText,
        emailTV: TextView,
        et_address: TextView,
        Address1: TextView,
        et_transport: TextView,
        Country: TextView,
//        State_ET: TextView, 
//        State_TV: TextView,
//        CityEt: TextView, 
//        CityTV: TextView, 
        ZipCodeET: EditText,
        ZipCodeTV: TextView,
        etAddressLL: LinearLayout


    ) {
        if (et_firstname.text.isEmpty()) {
            et_firstname.requestFocus()
            tv_firstname.visibility = View.VISIBLE
            tv_firstname.text = "*Please enter first name."
            et_firstname.setBackgroundResource(R.drawable.input_error)
        } else if (et_firstname.text.length < 2) {
            tv_firstname.visibility = View.VISIBLE
            tv_firstname.text = "*Please enter a valid first name."
            et_firstname.setBackgroundResource(R.drawable.input_error)

        } else if (et_lastname.text.isEmpty()) {
            tv_lastname.visibility = View.VISIBLE
            tv_firstname.visibility = View.GONE
            tv_lastname.text = "*Please enter last name."
            et_lastname.setBackgroundResource(R.drawable.input_error)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (et_lastname.text.length < 2) {
            tv_lastname.visibility = View.VISIBLE
            tv_firstname.visibility = View.GONE
            tv_lastname.text = "*Please enter a valid last name."
            et_lastname.setBackgroundResource(R.drawable.input_error)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (et_contactnumber.text.isEmpty()) {
            tv_contactnumber.visibility = View.VISIBLE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            tv_contactnumber.text = "*Please enter phone number."
            country.setBackgroundResource(R.drawable.input_error)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (et_contactnumber.text.trimStart().startsWith("0")) {
            tv_contactnumber.visibility = View.VISIBLE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            tv_contactnumber.text = "*Please enter a valid phone number."
            country.setBackgroundResource(R.drawable.input_error)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (et_contactnumber.text.length < 7) {
            tv_contactnumber.visibility = View.VISIBLE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            tv_contactnumber.text = "*Please enter a valid phone number."
            country.setBackgroundResource(R.drawable.input_error)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (EmailEt.text.isEmpty()) {
            emailTV.visibility = View.VISIBLE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            emailTV.text = "*Please enter email."
            EmailEt.setBackgroundResource(R.drawable.input_error)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (!(FormValidation.isEmail(EmailEt.text.toString()))) {
            emailTV.visibility = View.VISIBLE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            emailTV.text = "*Please enter a valid email."
            EmailEt.setBackgroundResource(R.drawable.input_error)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (et_address.text.isEmpty()) {
            Address1.visibility = View.VISIBLE
            emailTV.visibility = View.GONE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            Address1.text = "*Please enter your address."
            etAddressLL.setBackgroundResource(R.drawable.input_error)
            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (et_transport.text.isEmpty()) {
            Country.visibility = View.VISIBLE
            //CityTV.visibility = View.GONE
            Address1.visibility = View.GONE
            emailTV.visibility = View.GONE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            Country.text = "*Please select country."
            et_transport.setBackgroundResource(R.drawable.input_error)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        }
//        else if (State_ET.text.isEmpty()) {
//            Country.visibility = View.GONE
//            //CityTV.visibility = View.GONE
//            Address1.visibility = View.GONE
//            emailTV.visibility = View.GONE
//            tv_contactnumber.visibility = View.GONE
//            tv_lastname.visibility = View.GONE
//            tv_firstname.visibility = View.GONE
//            State_TV.visibility = View.VISIBLE
//            State_TV.text = "*Please select State."
//            State_ET.setBackgroundResource(R.drawable.input_error)
//            et_transport.setBackgroundResource(R.drawable.backgroundsearch)
//            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
//            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
//            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
//            country.setBackgroundResource(R.drawable.backgroundsearch)
//            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
//            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
//        } else if (CityEt.text.isEmpty()) {
//            CityTV.visibility = View.VISIBLE
//            Address1.visibility = View.GONE
//            emailTV.visibility = View.GONE
//            tv_contactnumber.visibility = View.GONE
//            tv_lastname.visibility = View.GONE
//            tv_firstname.visibility = View.GONE
//            //State_TV.visibility = View.GONE
//            CityTV.text = "*Please enter your city."
//            CityEt.setBackgroundResource(R.drawable.input_error)
//            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
//            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
//            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
//            country.setBackgroundResource(R.drawable.backgroundsearch)
//            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
//            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
//            et_transport.setBackgroundResource(R.drawable.backgroundsearch)
//        } 
        else if (ZipCodeET.text.isEmpty()) {
            ZipCodeTV.visibility = View.VISIBLE
            //CityTV.visibility = View.GONE
            Address1.visibility = View.GONE
            emailTV.visibility = View.GONE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            //State_TV.visibility = View.GONE
            ZipCodeTV.text = "*Please enter zipcode."
            ZipCodeET.setBackgroundResource(R.drawable.input_error)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
            et_transport.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (ZipCodeET.text.length < 4) {
            ZipCodeTV.visibility = View.VISIBLE
            //CityTV.visibility = View.GONE
            Address1.visibility = View.GONE
            emailTV.visibility = View.GONE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            //State_TV.visibility = View.GONE
            ZipCodeTV.text = "*Please enter valid zipcode."
            ZipCodeET.setBackgroundResource(R.drawable.input_error)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            et_firstname.setBackgroundResource(R.drawable.backgroundsearch)
            et_transport.setBackgroundResource(R.drawable.backgroundsearch)
        } else {
            ZipCodeTV.visibility = View.GONE
            Country.visibility = View.GONE
            //CityTV.visibility = View.GONE
            Address1.visibility = View.GONE
            emailTV.visibility = View.GONE
            tv_contactnumber.visibility = View.GONE
            tv_lastname.visibility = View.GONE
            tv_firstname.visibility = View.GONE
            //State_TV.visibility = View.GONE
            ZipCodeET.setBackgroundResource(R.drawable.backgroundsearch)
            //State_ET.setBackgroundResource(R.drawable.backgroundsearch)
            et_transport.setBackgroundResource(R.drawable.backgroundsearch)
            //CityEt.setBackgroundResource(R.drawable.backgroundsearch)
            etAddressLL.setBackgroundResource(R.drawable.backgroundsearch)
            EmailEt.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            et_lastname.setBackgroundResource(R.drawable.backgroundsearch)
            tv_firstname.setBackgroundResource(R.drawable.backgroundsearch)
        }
    }


    // validations for consumer register
    fun customerRegistser(
        firstNameET: EditText,
        firstNameTV: TextView,
        lastNameET: EditText,
        lastNameTV: TextView,
        phoneET: EditText,
        phoneTV: TextView,
        emailET: EditText,
        emailTV: TextView,
        passwordET: EditText,
        passwordTV: TextView,
        confirmPasswordET: EditText,
        confirmPasswordTV: TextView,
        CheckBox: CheckBox,
        termsTV: TextView,
        firstNameLinear: LinearLayout,
        lastNameLinear: LinearLayout,
        phoneLinear: LinearLayout,
        emailLinear: LinearLayout,
        passwordLinear: LinearLayout,
        confrimPasswordLinear: LinearLayout,
        country_ll: LinearLayout,
        address: EditText,
        address_tv: TextView,
        country1: TextView,
        country_tv: TextView,
        zipcode: EditText,
        zipcode_tv: TextView,
        address_ll: LinearLayout,
        zipcode_ll: LinearLayout,
        isSocial:Boolean
    ): Boolean {

        ClearFields(
            firstNameTV,
            lastNameTV,
            phoneTV,
            emailTV,
            passwordTV,
            confirmPasswordTV,
            termsTV,
            firstNameLinear,
            lastNameLinear,
            phoneLinear,
            emailLinear,
            passwordLinear,
            confrimPasswordLinear,
            address_tv,
            address_ll,
            country_tv,
            country_ll,
            zipcode_tv,
            zipcode_ll

        )

        val firstName = firstNameET.text.toString().trim()
        val lastName = lastNameET.text.toString().trim()
        val phone = phoneET.text.toString().trim()
        val email = emailET.text.toString().trim()
        val password = passwordET.text.toString().trim()
        val confrimPassword = confirmPasswordET.text.toString().trim()

        if (firstName.isEmpty()) {
            firstNameTV.visibility = View.VISIBLE
            firstNameTV.text = "*Please enter first name."
            firstNameLinear.setBackgroundResource(R.drawable.input_error)

        } else if (firstName.length < 2) {
            firstNameTV.visibility = View.VISIBLE
            firstNameTV.text = "*Please enter valid first name."
            firstNameLinear.setBackgroundResource(R.drawable.input_error)

        } else if (lastName.isEmpty()) {
            lastNameTV.visibility = View.VISIBLE
            lastNameTV.text = "*Please enter last name."
            lastNameLinear.setBackgroundResource(R.drawable.input_error)

        } else if (lastName.length < 2) {

            lastNameTV.visibility = View.VISIBLE
            lastNameTV.text = "*Please enter valid last name."
            lastNameLinear.setBackgroundResource(R.drawable.input_error)

        } else if (phone.isEmpty()) {

            phoneTV.visibility = View.VISIBLE
            phoneTV.text = "*Please enter mobile number."
            phoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (phone.length < 9) {

            phoneTV.visibility = View.VISIBLE
            phoneTV.text = "*Please enter valid mobile number."
            phoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (phone.trimStart().startsWith("0")) {
            phoneTV.visibility = View.VISIBLE
            phoneTV.text = "*Please enter valid mobile number."
            phoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (email.isEmpty()) {

            emailTV.visibility = View.VISIBLE
            emailTV.text = "*Please enter your email."
            emailLinear.setBackgroundResource(R.drawable.input_error)

        } else if (!(isEmail(emailET.text.toString()))) {
            emailTV.visibility = View.VISIBLE
            emailTV.text = "*Please enter valid email."
            emailLinear.setBackgroundResource(R.drawable.input_error)

        } else if (address.text.isEmpty()) {
            address_tv.visibility = View.VISIBLE
            address_tv.text = "*Please enter your address."
            address_ll.setBackgroundResource(R.drawable.input_error)
        } else if (country1.text.isEmpty()) {

            country_tv.visibility = View.VISIBLE
            country_tv.text = "*Please select country."
            country_ll.setBackgroundResource(R.drawable.input_error)

        }
        else if (zipcode.text.isEmpty()) {

            zipcode_tv.visibility = View.VISIBLE
            zipcode_tv.text = "*Please enter zipcode."
            zipcode_ll.setBackgroundResource(R.drawable.input_error)
        } else if (zipcode.text.length < 4) {
            zipcode_tv.visibility = View.VISIBLE
            zipcode_tv.text = "*Please enter valid zipcode."
            zipcode_ll.setBackgroundResource(R.drawable.input_error)

        } else if (password.isEmpty() && !isSocial) {

            passwordTV.visibility = View.VISIBLE
            passwordTV.setText("*Please enter password.")
            passwordLinear.setBackgroundResource(R.drawable.input_error)

        } else if (password.length < 6 && !isSocial) {

            passwordTV.visibility = View.VISIBLE
            passwordTV.setText("*Password length should be grater than 6.")
            passwordLinear.setBackgroundResource(R.drawable.input_error)

        } else if (confrimPassword.isEmpty() && !isSocial) {
            confirmPasswordTV.visibility = View.VISIBLE
            confirmPasswordTV.setText("*Please enter confirm password.")
            confrimPasswordLinear.setBackgroundResource(R.drawable.input_error)

        } else if (!password.equals(confrimPassword) && !isSocial ) {

            confirmPasswordTV.visibility = View.VISIBLE
            confirmPasswordTV.setText("*Confirm password should be same with \n  the password field.")
            confrimPasswordLinear.setBackgroundResource(R.drawable.input_error)

        } else {

            return true
        }
        return false
    }

    fun ClearFields(

        firstNameTV: TextView,
        lastNameTV: TextView,
        phoneTV: TextView,
        emailTV: TextView,
        passwordTV: TextView,
        confirmPasswordTV: TextView,
        termsTV: TextView,
        firstNameLinear: LinearLayout,
        lastNameLinear: LinearLayout,
        phoneLinear: LinearLayout,
        emailLinear: LinearLayout,
        passwordLinear: LinearLayout,
        confrimPasswordLinear: LinearLayout,
        address_tv: TextView,
        address_ll: LinearLayout,
        country_tv: TextView,
        country_ll: LinearLayout,
//        State_TV: TextView,
//        State_ET_ll: LinearLayout,
//        city_tv: TextView,
//        city_ll: LinearLayout,
        zipcode_tv: TextView,
        zipcode_ll: LinearLayout

    ) {
        firstNameTV.text = ""
        firstNameTV.visibility = View.GONE
        firstNameLinear.setBackgroundResource(R.drawable.backgroundsearch)

        lastNameTV.text = ""
        lastNameTV.visibility = View.GONE
        lastNameLinear.setBackgroundResource(R.drawable.backgroundsearch)

        phoneTV.text = ""
        phoneTV.visibility = View.GONE
        phoneLinear.setBackgroundResource(R.drawable.backgroundsearch)

        emailTV.text = ""
        emailTV.visibility = View.GONE
        emailLinear.setBackgroundResource(R.drawable.backgroundsearch)

        address_tv.text = ""
        address_tv.visibility = View.GONE
        address_ll.setBackgroundResource(R.drawable.backgroundsearch)

        country_tv.text = ""
        country_tv.visibility = View.GONE
        country_ll.setBackgroundResource(R.drawable.backgroundsearch)

        //state_tv.text = ""
        //State_TV.visibility = View.GONE
//        State_ET_ll.setBackgroundResource(R.drawable.backgroundsearch)
//
//
//        //city_tv.text = ""
//        city_tv.visibility = View.GONE
//        city_ll.setBackgroundResource(R.drawable.backgroundsearch)

        zipcode_tv.text = ""
        zipcode_tv.visibility = View.GONE
        zipcode_ll.setBackgroundResource(R.drawable.backgroundsearch)

        passwordTV.text = ""
        passwordTV.visibility = View.GONE
        passwordLinear.setBackgroundResource(R.drawable.backgroundsearch)

        confirmPasswordTV.visibility = View.GONE
        confirmPasswordTV.text = ""
        confrimPasswordLinear.setBackgroundResource(R.drawable.backgroundsearch)



        termsTV.text = ""


    }


    fun RetailerRegistrationValidation(
        firstname_et: EditText,
        first_name: TextView,
        LastName_et: EditText,
        surname_tv: TextView,
        email_et: EditText,
        email_tv: TextView,
        mobile_editText: EditText,
        mobile_tv: TextView,
        country: LinearLayout,
        address: EditText,
        address_tv: TextView,
        country1: TextView,
        country_tv: TextView,
        zipcode: EditText,
        zipcode_tv: TextView,
        password_et: EditText,
        password_tv: TextView,
        PaswordLL: LinearLayout,
        confirmpassword_et: EditText,
        confirmpassword_tv: TextView,
        ConfirmPaswordLL: LinearLayout,
        addressLL: LinearLayout,
        isSocial:Boolean
    ) {
        if (firstname_et.text.isEmpty()) {
            first_name.text = "*Please enter your first name."
            firstname_et.setBackgroundResource(R.drawable.input_error)

        } else if (firstname_et.text.length < 2) {
            first_name.text = "*Please enter valid first name."
            firstname_et.setBackgroundResource(R.drawable.input_error)

        } else if (LastName_et.text.isEmpty()) {
            first_name.text = ""
            surname_tv.text = "*Please enter your lastname."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.input_error)
        } else if (LastName_et.text.length < 2) {
            first_name.text = ""
            surname_tv.text = "*Please enter your lastname."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.input_error)
        } else if (email_et.text.isEmpty()) {
            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = "*Please enter your email address."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.input_error)
        } else if (!email_et.text.matches(Regex(emailPattern))) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = "*Please enter valid email address."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.input_error)
        } else if (mobile_editText.text.isEmpty()) {
            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = "*Please enter your mobile number."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (mobile_editText.text.length < 9) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = "*Please enter valid mobile number."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (mobile_editText.text.trimStart().startsWith("0")) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = "*Please enter valid mobile number."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.input_error)
        } else if (address.text.isEmpty()) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = "*Please enter your address."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.input_error)
        } else if (country1.text.isEmpty()) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = ""
            country_tv.text = "*Please select country."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.input_error)

        }
        else if (zipcode.text.isEmpty()) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = "*Please enter zipcode."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.input_error)
        } else if (zipcode.text.length < 4) {

            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = "*Please enter valid zipcode."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.input_error)

        } else if (password_et.text.isEmpty() && !isSocial) {
            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = ""
            password_tv.text = "*Please enter password."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (password_et.text.length < 6 && !isSocial) {
            first_name.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            address_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = ""
            password_tv.text = "*Password length should be grater than 6."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (confirmpassword_et.text.isEmpty() && !isSocial) {

            first_name.text = ""
            password_tv.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = ""
            address_tv.text = ""
            confirmpassword_tv.text = "*Please enter confirm password."
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.input_error)
        } else if (!confirmpassword_et.text.toString().equals(password_et.text.toString()) && !isSocial) {

            first_name.text = ""
            password_tv.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = ""
            address_tv.text = ""
            confirmpassword_tv.text = "*Confirm password doesn't matched"
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.input_error)
        } else {
            first_name.text = ""
            password_tv.text = ""
            surname_tv.text = ""
            email_tv.text = ""
            mobile_tv.text = ""
            country_tv.text = ""
            zipcode_tv.text = ""
            address_tv.text = ""
            confirmpassword_tv.text = ""
            firstname_et.setBackgroundResource(R.drawable.backgroundsearch)
            LastName_et.setBackgroundResource(R.drawable.backgroundsearch)
            email_et.setBackgroundResource(R.drawable.backgroundsearch)
            country.setBackgroundResource(R.drawable.backgroundsearch)
            addressLL.setBackgroundResource(R.drawable.backgroundsearch)
            zipcode.setBackgroundResource(R.drawable.backgroundsearch)
            country1.setBackgroundResource(R.drawable.backgroundsearch)
            PaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPaswordLL.setBackgroundResource(R.drawable.backgroundsearch)
        }


    }



    fun email(
        email: String, et1: EditText, tv1: TextView, tv2: TextView, v1: View
    ) {
        v1.setBackgroundColor(Color.parseColor("#979797"))

        if (email.isEmpty()) {
            et1.requestFocus()
            tv2.text = "*Please enter email."
            tv2.setTextColor(Color.parseColor("#FF0808"))
            v1.setBackgroundColor(Color.parseColor("#FF0808"))

        } else if (!email.matches(Regex(emailPattern))) {
            et1.requestFocus()
            tv1.text = "*Please enter valid email."
            tv1.setTextColor(Color.parseColor("#FF0808"))
            tv2.text = "EMAIL"
            tv2.setTextColor(Color.parseColor("#FF0808"))
            v1.setBackgroundColor(Color.parseColor("#FF0808"))

        } else {
            tv1.text = ""
            tv2.text = ""
        }
    }


    fun otp(
        email: String, etotp: EditText, tvotp: TextView, votp: View
    ) {
        votp.setBackgroundColor(Color.parseColor("#979797"))

        if (email.isEmpty()) {
            etotp.requestFocus()
            tvotp.text = "*Required."
            tvotp.setTextColor(Color.parseColor("#FF0808"))
            votp.setBackgroundColor(Color.parseColor("#FF0808"))

        } else if (!email.equals("123456")) {
            etotp.requestFocus()
            tvotp.text = "*Please enter valid OTP."
            tvotp.setTextColor(Color.parseColor("#FF0808"))
            votp.setBackgroundColor(Color.parseColor("#FF0808"))

        } else {
            tvotp.text = ""
        }
    }


    fun isEmail(email: String): Boolean {
        var returnvalue: Boolean
        returnvalue = email.matches(Regex(emailPattern))
        return returnvalue
    }


    fun isPhone(password: String): Boolean {
        var returnvalue: Boolean
        if (password.matches(Regex(MobilePattern))) {
            returnvalue = true
        } else {
            returnvalue = false
        }
        return returnvalue
    }

    fun CustomerLogin(
        Email_et: EditText, Email_tv: TextView,
        password_et: EditText, password_tv: TextView,
        PhoneLinear: LinearLayout, passwordLinear: LinearLayout

    ) {

        Email_tv.text = ""
        PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)

        if (Email_et.text.isEmpty()) {
            Email_tv.text = "*Please enter your email address."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)
        } else if (!Email_et.text.matches(Regex(emailPattern))) {

            Email_tv.text = "*Please enter valid email address."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (password_et.text.isEmpty()) {
            Email_tv.text = ""
            password_tv.text = "*Please enter password."
            PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)
            passwordLinear.setBackgroundResource(R.drawable.input_error)
        } else {
            Email_tv.text = ""
            password_tv.text = ""
            passwordLinear.setBackgroundResource(R.drawable.backgroundsearch)
        }

    }

    fun RetailerLogin(
        Email_et: EditText, Email_tv: TextView,
        password_et: EditText, password_tv: TextView,
        PhoneLinear: LinearLayout, passwordLinear: LinearLayout
    ) {
        if (Email_et.text.isEmpty()) {
            Email_tv.text = "*Please enter email address."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)
        } else if (!Email_et.text.matches(Regex(emailPattern))) {

            Email_tv.text = "*Please enter valid email address."
            PhoneLinear.setBackgroundResource(R.drawable.input_error)

        } else if (password_et.text.isEmpty()) {
            Email_tv.text = ""
            password_tv.text = "*Please enter password."
            PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)
            passwordLinear.setBackgroundResource(R.drawable.input_error)
        } else {
            Email_tv.text = ""
            password_tv.text = ""
            PhoneLinear.setBackgroundResource(R.drawable.backgroundsearch)
            passwordLinear.setBackgroundResource(R.drawable.backgroundsearch)

        }

    }

    fun Retailer_fillform(
        first_name: EditText,
        first_nametv: TextView,
        lastname: EditText,
        lastname_tv: TextView,
        emailaddress: EditText,
        email_tv: TextView,
        unique_product: EditText,
        uniqueproduct_tv: TextView,
        et_brand_name: EditText,
        BrandList: ArrayList<BrandList>,
        list_brands_tv: TextView,
        companyname: EditText,
        companyname_tv: TextView,
        registration_number: EditText,
        registration_tv: TextView,
        RB_yes: RadioButton,
        RB_no: RadioButton,
        Vat_ET: EditText,
        VAT_TV: TextView,
        values: Boolean = false,
        Monthly_RevenueTextview: TextView,
        bankname: EditText,
        bankname_tv: TextView,
        brachname: EditText,
        Branchname_tv: TextView,
        branchcode: EditText,
        Branchcode_tv: TextView,
        swiftcode: EditText,
        SWIFTcode_tv: TextView,
        accountname: EditText,
        accountname_tv: TextView,
        accountnumber: EditText,
        accountnumber_tv: TextView,
        account_type_ll: LinearLayout,
        accounttype_tv: TextView,
        requestAccountType: String,
        physical_yes: RadioButton,
        physical_no: RadioButton,
        store_name_ET: EditText,
        store_name_tv: TextView,
        store_address_ET: TextView,
        store_address_tv: TextView,
        store_contact_ET: EditText,
        store_contact_tv: TextView,

        ) {
        if (first_name.text.isEmpty()) {
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            first_nametv.text = "*Please enter first name."
            first_name.setBackgroundResource(R.drawable.input_error)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (lastname.text.isEmpty()) {
            first_nametv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            lastname_tv.text = "*Please enter last name."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.input_error)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (emailaddress.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            email_tv.text = "*Please enter email address."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.input_error)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (!emailaddress.text.matches(Regex(emailPattern))) {
            first_nametv.text = ""
            lastname_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            email_tv.text = "*Please enter valid email address."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.input_error)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (unique_product.text.isEmpty()) {
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            uniqueproduct_tv.text = "*Please enter number of unique products."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.input_error)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (BrandList.size.equals(0)) {
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            list_brands_tv.visibility = View.VISIBLE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.input_error)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
        } else if ((physical_yes.isChecked == true && store_name_ET.text.isEmpty())) {
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = ""
            store_name_tv.text = "*Please enter store name."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.input_error)


        } else if ((physical_yes.isChecked == true && store_address_ET.text.isEmpty())) {
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = ""
            store_name_tv.text = ""
            store_address_tv.text = "*Please enter store address."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.input_error)


        } else if ((physical_yes.isChecked == true && store_contact_ET.text.isEmpty())) {
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = ""
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = "*Please enter store contact."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.input_error)


        } else if ((physical_yes.isChecked == true && store_contact_ET.text.length < 9)) {
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = ""
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = "*Please enter valid store contact."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.input_error)


        } else if ((physical_yes.isChecked == true && store_contact_ET.text.trimStart()
                .startsWith("0"))
        ) {
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = ""
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = "*Please enter valid store contact."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.input_error)

        } else if (companyname.text.isEmpty()) {

            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            companyname_tv.text = "*Please enter company name."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.input_error)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)


        } else if (registration_number.text.isEmpty()) {

            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            registration_tv.text = "*Please enter business registration number."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.input_error)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)


        } else if ((RB_yes.isChecked == true && Vat_ET.text.isEmpty())) {

            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            VAT_TV.text = "*Please enter VAT number."
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.input_error)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)

            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (values == false) {

            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = "*Please select atleast one option."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (bankname.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            accounttype_tv.text = ""
            accountname_tv.text = ""
            VAT_TV.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Monthly_RevenueTextview.text = ""
            bankname_tv.text = "*Please enter bank name."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.input_error)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (brachname.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accountname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Branchname_tv.text = "*Please enter branch name."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.input_error)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (branchcode.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            Monthly_RevenueTextview.text = ""
            VAT_TV.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            Branchcode_tv.text = "*Please enter branch code"
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.input_error)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (swiftcode.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            Monthly_RevenueTextview.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            accounttype_tv.text = ""
            VAT_TV.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            accountnumber_tv.text = ""
            SWIFTcode_tv.text = "*Please enter SWIFT code"
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.input_error)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (requestAccountType.equals("Select")) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            Monthly_RevenueTextview.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            accounttype_tv.text = "*Please select account type."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.input_error)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)
        } else if (accountname.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            Monthly_RevenueTextview.text = ""
            bankname_tv.text = ""
            VAT_TV.text = ""
            accounttype_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            accountnumber_tv.text = ""
            accountname_tv.text = "*Please enter account name."
            list_brands_tv.visibility = View.GONE
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.input_error)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)

        } else if (accountnumber.text.isEmpty()) {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            VAT_TV.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            list_brands_tv.visibility = View.GONE
            accountnumber_tv.text = "*Please enter account number."
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.input_error)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)
        } else {
            store_name_tv.text = ""
            store_address_tv.text = ""
            store_contact_tv.text = ""
            first_nametv.text = ""
            lastname_tv.text = ""
            email_tv.text = ""
            uniqueproduct_tv.text = ""
            companyname_tv.text = ""
            registration_tv.text = ""
            bankname_tv.text = ""
            Monthly_RevenueTextview.text = ""
            accounttype_tv.text = ""
            VAT_TV.text = ""
            accountname_tv.text = ""
            Branchname_tv.text = ""
            Branchcode_tv.text = ""
            SWIFTcode_tv.text = ""
            list_brands_tv.visibility = View.GONE
            accountnumber_tv.text = ""
            first_name.setBackgroundResource(R.drawable.backgroundsearch)
            lastname.setBackgroundResource(R.drawable.backgroundsearch)
            emailaddress.setBackgroundResource(R.drawable.backgroundsearch)
            unique_product.setBackgroundResource(R.drawable.backgroundsearch)
            et_brand_name.setBackgroundResource(R.drawable.backgroundsearch)
            companyname.setBackgroundResource(R.drawable.backgroundsearch)
            registration_number.setBackgroundResource(R.drawable.backgroundsearch)
            Vat_ET.setBackgroundResource(R.drawable.backgroundsearch)
            bankname.setBackgroundResource(R.drawable.backgroundsearch)
            brachname.setBackgroundResource(R.drawable.backgroundsearch)
            branchcode.setBackgroundResource(R.drawable.backgroundsearch)
            swiftcode.setBackgroundResource(R.drawable.backgroundsearch)
            account_type_ll.setBackgroundResource(R.drawable.backgroundsearch)
            accountname.setBackgroundResource(R.drawable.backgroundsearch)
            accountnumber.setBackgroundResource(R.drawable.backgroundsearch)
            store_name_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_address_ET.setBackgroundResource(R.drawable.backgroundsearch)
            store_contact_ET.setBackgroundResource(R.drawable.backgroundsearch)
        }
    }


    fun editprofile(
        firstname: EditText,
        errorTvEditFirstName: TextView,
        lastname: EditText,
        errorTvEditLastName: TextView,
        MobileNumber_EditText: EditText,
        errorTvEditContactNumber: TextView,
        postalcode: EditText,
        errorTvEditPostCode: TextView,
        address: EditText,
        errorTvEditAddress: TextView,
        country1: TextView,
        Country: TextView,
//        State_ET: TextView,
//        State_TV: TextView,
//        CityEt: TextView,
//        CityTV: TextView,
        Email_ET: EditText,
        Email_TV: TextView,
        addressLine1LL: LinearLayout,

        ) {

        if (firstname.text.isEmpty()) {
            errorTvEditFirstName.text = "*Please enter your first name."
        } else if (firstname.text.length < 2) {
            firstname.requestFocus()
            errorTvEditFirstName.text = "*Please enter valid first name."

        } else if (lastname.text.isEmpty()) {
            errorTvEditFirstName.text = ""
            errorTvEditLastName.text = "*Please enter your last name."
        } else if (lastname.text.length < 2) {
            errorTvEditFirstName.text = ""
            errorTvEditLastName.text = "*Please enter valid last name."

        } else if (Email_ET.text.isEmpty()) {
            errorTvEditFirstName.text = ""
            errorTvEditLastName.text = ""
            Email_TV.text = "*Please enter your email address."
        } else if (!Email_ET.text.matches(Regex(emailPattern))) {
            errorTvEditFirstName.text = ""
            errorTvEditLastName.text = ""
            Email_TV.text = "*Please enter valid email address."
        } else if (MobileNumber_EditText.text.isEmpty()) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            Email_TV.text = ""
            errorTvEditContactNumber.text = "*Please enter your mobile number."
        } else if (MobileNumber_EditText.text.length < 9) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            Email_TV.text = ""
            errorTvEditContactNumber.text = "*Please enter valid mobile number."

        } else if (MobileNumber_EditText.text.trimStart().startsWith("0")) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            Email_TV.text = ""
            errorTvEditContactNumber.text = "*Please enter valid mobile number."
        } else if (address.text.isEmpty()) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            errorTvEditContactNumber.text = ""
            Email_TV.text = ""
            errorTvEditAddress.text = "*Please enter your address."
        } else if (country1.text.isEmpty()) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            errorTvEditContactNumber.text = ""
            errorTvEditAddress.text = ""
            Email_TV.text = ""
            Country.text = "*Please select country."

        }
//        else if (State_ET.text.isEmpty()) {
//            errorTvEditLastName.text = ""
//            errorTvEditFirstName.text = ""
//            errorTvEditContactNumber.text = ""
//            errorTvEditPostCode.text = ""
//            errorTvEditAddress.text = ""
//            Country.text = ""
//            Email_TV.text = ""
//            State_TV.text = "*Please select State."
//
//        } else if (CityEt.text.isEmpty()) {
//            errorTvEditLastName.text = ""
//            errorTvEditFirstName.text = ""
//            errorTvEditContactNumber.text = ""
//            errorTvEditPostCode.text = ""
//            errorTvEditAddress.text = ""
//            Country.text = ""
//            //state_tv.text = ""
//            Email_TV.text = ""
//            CityTV.text = "*Please enter your city."
//
//        }
        else if (postalcode.text.isEmpty()) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            errorTvEditContactNumber.text = ""
            errorTvEditPostCode.text = ""
            errorTvEditAddress.text = ""
            Country.text = ""
            //state_tv.text = ""
            ////CityTV.text = ""
            Email_TV.text = ""
            errorTvEditPostCode.text = "*Please enter your zipcode."
        } else if (postalcode.text.length < 4) {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            errorTvEditContactNumber.text = ""
            errorTvEditPostCode.text = ""
            errorTvEditAddress.text = ""
            Country.text = ""
            //state_tv.text = ""
            ////CityTV.text = ""
            Email_TV.text = ""
            errorTvEditPostCode.text = "*Please enter valid zipcode."
        } else {
            errorTvEditLastName.text = ""
            errorTvEditFirstName.text = ""
            errorTvEditContactNumber.text = ""
            errorTvEditPostCode.text = ""
            errorTvEditAddress.text = ""
            Country.text = ""
            //state_tv.text = ""
            ////CityTV.text = ""
            Email_TV.text = ""
        }
    }

    fun ChangePassword(
        OldPasswordET: EditText,
        OldPasswordTV: TextView,
        OldPassword: LinearLayout,
        PasswordET: EditText,
        NewPasswordTV: TextView,
        PasswordLL: LinearLayout,
        ConfirmPasswordET: EditText,
        ConfirmPasswordTv: TextView,
        ConfirmPasswordLL: LinearLayout
    ) {

        if (OldPasswordET.text.isEmpty()) {
            OldPasswordTV.visibility = View.VISIBLE
            OldPasswordTV.text = "*Please enter your old password."
            OldPassword.setBackgroundResource(R.drawable.input_error)

        } else if (PasswordET.text.isEmpty()) {
            NewPasswordTV.visibility = View.VISIBLE
            OldPasswordTV.visibility = View.GONE
            NewPasswordTV.text = "*Please enter new password."
            OldPassword.setBackgroundResource(R.drawable.backgroundsearch)
            PasswordLL.setBackgroundResource(R.drawable.input_error)

        } else if (ConfirmPasswordET.text.isEmpty()) {
            ConfirmPasswordTv.visibility = View.VISIBLE
            NewPasswordTV.visibility = View.GONE
            OldPasswordTV.visibility = View.GONE
            ConfirmPasswordTv.setText("*Please enter confirm password.")
            OldPassword.setBackgroundResource(R.drawable.backgroundsearch)
            PasswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPasswordLL.setBackgroundResource(R.drawable.input_error)

        } else if (!PasswordET.text.toString().equals(ConfirmPasswordET.text.toString())) {
            ConfirmPasswordTv.visibility = View.VISIBLE
            NewPasswordTV.visibility = View.GONE
            OldPasswordTV.visibility = View.GONE
            ConfirmPasswordTv.setText("*Confirm password doesn't matched.")
            OldPassword.setBackgroundResource(R.drawable.backgroundsearch)
            PasswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPasswordLL.setBackgroundResource(R.drawable.input_error)

        } else {
            ConfirmPasswordTv.visibility = View.GONE
            NewPasswordTV.visibility = View.GONE
            OldPasswordTV.visibility = View.GONE
            OldPassword.setBackgroundResource(R.drawable.backgroundsearch)
            PasswordLL.setBackgroundResource(R.drawable.backgroundsearch)
            ConfirmPasswordLL.setBackgroundResource(R.drawable.backgroundsearch)
        }
    }



    fun validationForBusinessFormCommon(
        firstNameEt: EditText,
        firstNameTV: TextView,
        lastNameEt: EditText,
        lastNameTV: TextView,
        emailEt: EditText,
        emailTV: TextView,
        driverLicenseEt: EditText,
        riverLicenseTV: TextView,
        vehicleTypeEt: EditText,
        vehicleTypeTV: TextView,
        makeAndModelEt: EditText,
        vehicleMakeAndModelTV: TextView,
        vehicleYearEt: EditText,
        vehicleYearTV: TextView,
        vehicleColorEt: EditText,
        vehicleColorTV: TextView,
        vehicleInsuranceInformationEt: EditText,
        vehicleInsuranceInformationTV: TextView,
        bankNameEt: EditText,
        bankNameTV: TextView,
        BranchNameEt: EditText,
        branchNameTV: TextView,
        branchCodeEt: EditText,
        branchCode: TextView,
        swiftCodeEt: EditText,
        siftCodeTV: TextView,
        accountTypeEt: EditText,
        accountTypeTV: TextView,
        accountHolderEt: EditText,
        accountHolderTV: TextView,
        accountNumberEt: EditText,
        accountNumberTV: TextView,
        mobileNumbeEt: EditText,
        mobileNumberTV: TextView
    ): Boolean {

        firstNameTV.text = ""
        lastNameTV.text = ""
        mobileNumberTV.text = ""
        emailTV.text = ""
        riverLicenseTV.text = ""
        vehicleTypeTV.text = ""
        vehicleMakeAndModelTV.text = ""
        vehicleYearTV.text = ""
        vehicleColorTV.text = ""
        vehicleInsuranceInformationTV.text = ""
        bankNameTV.text = ""
        branchNameTV.text = ""
        branchCode.text = ""
        siftCodeTV.text = ""
        accountTypeTV.text = ""
        accountHolderTV.text = ""
        accountNumberTV.text = ""

        firstNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        lastNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        mobileNumbeEt.setBackgroundResource(R.drawable.backgroundsearch)
        emailEt.setBackgroundResource(R.drawable.backgroundsearch)
        driverLicenseEt.setBackgroundResource(R.drawable.backgroundsearch)
        vehicleTypeEt.setBackgroundResource(R.drawable.backgroundsearch)
        makeAndModelEt.setBackgroundResource(R.drawable.backgroundsearch)
        vehicleYearEt.setBackgroundResource(R.drawable.backgroundsearch)
        vehicleColorEt.setBackgroundResource(R.drawable.backgroundsearch)
        vehicleInsuranceInformationEt.setBackgroundResource(R.drawable.backgroundsearch)
        bankNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        BranchNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        branchCodeEt.setBackgroundResource(R.drawable.backgroundsearch)
        swiftCodeEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountTypeEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountHolderEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountNumberEt.setBackgroundResource(R.drawable.backgroundsearch)


        if (firstNameEt.text.toString().isEmpty()){
            firstNameTV.text = "Please enter first name"
            firstNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(lastNameEt.text.toString().isEmpty()){
            lastNameTV.text = "Please enter last name"
            lastNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(mobileNumbeEt.text.toString().isEmpty()){
            mobileNumberTV.text = "Please enter mobile number"
            mobileNumbeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(mobileNumbeEt.text.length < 9){
            mobileNumberTV.text = "Please enter valid mobile number"
            mobileNumbeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(emailEt.text.toString().isEmpty()){
            emailTV.text = "Please enter email"
            emailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(!(isEmail(emailEt.text.toString()))){
            emailTV.text = "Please enter valid email"
            emailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(driverLicenseEt.text.toString().isEmpty()){
            riverLicenseTV.text = "Please Enter Driver's License"
            driverLicenseEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(vehicleTypeEt.text.toString().isEmpty()){
            vehicleTypeTV.text = "Please Enter Vehicle Type"
            vehicleTypeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(makeAndModelEt.text.toString().isEmpty()){
            vehicleMakeAndModelTV.text = "Please Enter Make and Model"
            makeAndModelEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(vehicleYearEt.text.toString().isEmpty()){
            vehicleYearTV.text = "Please Enter Vehicle Year"
            vehicleYearEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(vehicleColorEt.text.toString().isEmpty()){
            vehicleColorTV.text = "Please Enter Vehicle Color"
            vehicleColorEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(vehicleInsuranceInformationEt.text.toString().isEmpty()){
            vehicleInsuranceInformationTV.text = "Please Enter Vehicle Insurance Information"
            vehicleInsuranceInformationEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(bankNameEt.text.toString().isEmpty()){
            bankNameTV.text = "Please Enter Bank Name"
            bankNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(BranchNameEt.text.toString().isEmpty()){
            branchNameTV.text = "Please Enter Branch name"
            BranchNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(branchCodeEt.text.toString().isEmpty()){
            branchCode.text = "Please enter branch code"
            branchCodeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(swiftCodeEt.text.toString().isEmpty()){
            siftCodeTV.text = "Please Enter Swift Code"
            swiftCodeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountTypeEt.text.toString().isEmpty()){
            accountTypeTV.text = "Please Enter Account Type"
            accountTypeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountHolderEt.text.toString().isEmpty()){
            accountHolderTV.text = "Please Enter Account Holder"
            accountHolderEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountNumberEt.text.toString().isEmpty()){
            accountNumberTV.text = "Please Enter Account Holder"
            accountNumberEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else{
            return true
        }

    }


    fun validationForBusinessFormRetailer(
        firstNameEt: EditText,
        firstNameTV: TextView,
        lastNameEt: EditText,
        lastNameTV: TextView,
        emailEt: EditText,
        emailTV: TextView,
        businessNameEt: EditText,
        businessNameTV: TextView,
        businessTypeEt: Spinner,
        businessTypeTV: TextView,
        businessAddressEt: EditText,
        businessAddressTV: TextView,
        businessEmailEt: EditText,
        businessEmailTV: TextView,
        einEt: EditText,
        eintv: TextView,
        orderVolumeET: EditText,
        orderVolumeTV: TextView,
        bankNameEt: EditText,
        bankNameTV: TextView,
        branchNameEt: EditText,
        branchNameTV: TextView,
        branchCodeEt: EditText,
        branchCode: TextView,
        swiftCodeEt: EditText,
        siftCodeTV: TextView,
        accountTypeEt: EditText,
        accountTypeTV: TextView,
        accountHolderEt: EditText,
        accountHolderTV: TextView,
        accountNumberEt: EditText,
        accountNumberTV: TextView,
        mobileNumbeEt: EditText,
        mobileNumberTV: TextView,
        BusinessTypeSpinner: LinearLayout,
    ): Boolean {

        firstNameTV.text = ""
        lastNameTV.text = ""
        mobileNumberTV.text = ""
        emailTV.text = ""
        businessNameTV.text = ""
        businessTypeTV.text = ""
        businessAddressTV.text = ""
        businessEmailTV.text = ""
        eintv.text = ""
        orderVolumeTV.text = ""
        bankNameTV.text = ""
        branchNameTV.text = ""
        branchCode.text = ""
        siftCodeTV.text = ""
        accountTypeTV.text = ""
        accountHolderTV.text = ""
        accountNumberTV.text = ""

        firstNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        lastNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        mobileNumbeEt.setBackgroundResource(R.drawable.backgroundsearch)
        emailEt.setBackgroundResource(R.drawable.backgroundsearch)
        businessNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        BusinessTypeSpinner.setBackgroundResource(R.drawable.backgroundsearch)
        businessAddressEt.setBackgroundResource(R.drawable.backgroundsearch)
        businessEmailEt.setBackgroundResource(R.drawable.backgroundsearch)
        einEt.setBackgroundResource(R.drawable.backgroundsearch)
        orderVolumeET.setBackgroundResource(R.drawable.backgroundsearch)
        bankNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        branchNameEt.setBackgroundResource(R.drawable.backgroundsearch)
        branchCodeEt.setBackgroundResource(R.drawable.backgroundsearch)
        swiftCodeEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountTypeEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountHolderEt.setBackgroundResource(R.drawable.backgroundsearch)
        accountNumberEt.setBackgroundResource(R.drawable.backgroundsearch)



        if (firstNameEt.text.toString().isEmpty()){
            firstNameTV.text = "Please enter first name"
            firstNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(lastNameEt.text.toString().isEmpty()){
            lastNameTV.text = "Please enter last name"
            lastNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(mobileNumbeEt.text.toString().isEmpty()){
            mobileNumberTV.text = "Please enter mobile number"
            mobileNumbeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(mobileNumbeEt.text.length < 9){
            mobileNumberTV.text = "Please enter valid mobile number"
            mobileNumbeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(emailEt.text.toString().isEmpty()){
            emailTV.text = "Please enter email"
            emailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(!(isEmail(emailEt.text.toString()))){
            emailTV.text = "Please enter valid email"
            emailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(businessNameEt.text.toString().isEmpty()){
            businessNameTV.text = "Please enter business name."
            businessNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(businessTypeEt.selectedItem == "Select business type"){
            businessTypeTV.text = "Please select business type"
            BusinessTypeSpinner.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(businessAddressEt.text.toString().isEmpty()){
            businessAddressTV.text = "Please enter business address"
            businessAddressEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(businessEmailEt.text.toString().isEmpty()){
            businessEmailTV.text = "Please enter business email."
            businessEmailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(!(isEmail(businessEmailEt.text.toString()))){
            businessEmailTV.text = "Please enter valid email."
            businessEmailEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(einEt.text.toString().isEmpty()){
            eintv.text = "Please enter EIN or SSN"
            einEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(orderVolumeET.text.toString().isEmpty()){
            orderVolumeTV.text = "Please enter number of orders you expect to fulfill"
            orderVolumeET.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(bankNameEt.text.toString().isEmpty()){
            bankNameTV.text = "Please Enter Bank Name"
            bankNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(branchNameEt.text.toString().isEmpty()){
            branchNameTV.text = "Please Enter Branch name"
            branchNameEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(branchCodeEt.text.toString().isEmpty()){
            branchCode.text = "Please enter branch code"
            branchCodeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(swiftCodeEt.text.toString().isEmpty()){
            siftCodeTV.text = "Please Enter Swift Code"
            swiftCodeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountTypeEt.text.toString().isEmpty()){
            accountTypeTV.text = "Please Enter Account Type"
            accountTypeEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountHolderEt.text.toString().isEmpty()){
            accountHolderTV.text = "Please Enter Account Holder"
            accountHolderEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else if(accountNumberEt.text.toString().isEmpty()){
            accountNumberTV.text = "Please Enter Account Holder"
            accountNumberEt.setBackgroundResource(R.drawable.input_error)
            return false
        }else{
            return true
        }

    }





}





