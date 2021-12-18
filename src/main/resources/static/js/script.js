$(function() {

    var owner = $('#cardName');
    var cardNumber = $('#cardNumber');
    var cardNumberField = $('#card-number-field');
    var CVV = $("#cvv");
    var mastercard = $("#mastercard");
    var confirmButton = $('#confirm');
    var visa = $("#visa");
    var amex = $("#amex");
    var expiryYear = $('#ccexpiryyear');
    var expiryMonth = $('#ccexpirymonth');

    // Use the payform library to format and validate
    // the payment fields.

    cardNumber.payform('formatCardNumber');
    CVV.payform('formatCardCVC');


    cardNumber.keyup(function() {

        amex.removeClass('transparent');
        visa.removeClass('transparent');
        mastercard.removeClass('transparent');


        if ($.payform.validateCardNumber(cardNumber.val()) == false) {
            cardNumberField.addClass('has-error');
            cardNumber.addClass('has-error');
        } else {
            cardNumberField.removeClass('has-error');
            cardNumberField.addClass('has-success');
        }

        if ($.payform.parseCardType(cardNumber.val()) == 'visa') {
            mastercard.addClass('transparent');
            amex.addClass('transparent');
        } else if ($.payform.parseCardType(cardNumber.val()) == 'amex') {
            mastercard.addClass('transparent');
            visa.addClass('transparent');
        } else if ($.payform.parseCardType(cardNumber.val()) == 'mastercard') {
            amex.addClass('transparent');
            visa.addClass('transparent');
        }
    });

    confirmButton.click(function(e) {

        //var isCardValid = $.payform.validateCardNumber(cardNumber.val());
        var isCardValid = cardNumber.val().length >= 16;
        var isCvvValid = $.payform.validateCardCVC(CVV.val());
        var isExpiryValid = $.payform.validateCardExpiry(expiryMonth.val(), expiryYear.val());

        owner.css({ "border": '1px solid #ced4da'});
        cardNumber.css({ "border": '1px solid #ced4da'});
        CVV.css({ "border": '1px solid #ced4da'});
        expiryMonth.css({ "border": '1px solid #ced4da'});
        expiryYear.css({ "border": '1px solid #ced4da'});

        if(owner.val().length < 5){
            owner.css({ "border": '#FF0000 1px solid'});
            e.preventDefault();
        } else if (!isCardValid) {
            cardNumber.css({ "border": '#FF0000 1px solid'});
            e.preventDefault();
        }else if (!isExpiryValid) {
             expiryMonth.css({ "border": '#FF0000 1px solid'});
             expiryYear.css({ "border": '#FF0000 1px solid'});
             e.preventDefault();
         } else if (!isCvvValid) {
            CVV.css({ "border": '#FF0000 1px solid'});
            e.preventDefault();
        }  else {
            // Everything is correct. Add your form submission code here.
            //alert('Validation Success');
            $('#paymentForm').submit();

        }
    });
});
