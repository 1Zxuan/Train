(function($) {
    $.fn.dataTableEditor = function(options) {
        // Settings
        var settings = $.extend({
            postURL: "SET POSTURL",
            success: function() {},
            error: function() {},
            complete: function() {}
        }, options);

        // Add a class to the table for later reference
        this.addClass("dtEditor");

        // Handle td click events
        $(document).on('click', '.dtEditor td', function() {
            // Ignore clicks if currently editing
            if ($(this).hasClass("editing"))
                return;

            (function(td) {
                // Create an input element
                var type = td.data("type");
                var field = td.data("field");
                var value = td.text();
                var input = $("<input name='"+field+"' type='"+type+"' value='"+value+"'>");

                // Bind to click-away
                input.on('change blur', function() {
                    var input = $(this);
                    var val = $(this).val();
                    var field = $(this).attr("name");
                    var identity = $(this).closest("tr").data("identity");

                    // POST new data
                    $.ajax({
                        type: "POST",
                        url: settings.postURL,
                        data: {
                            id: identity,
                            field: field,
                            value: val
                        },
                        success: function(data) {
                            td.text(data);
                            settings.success(td, data);
                        },
                        error: function(req, status, error) {
                            td.text(error);
                            settings.error(td, req, status, error);
                        },
                        complete: function() {
                            td.removeClass("editing");
                            settings.complete(td);
                        }
                    });
                });
                // Replace td content with the new input element and focus
                td.html(input);
                td.addClass("editing");
                input.focus();
            })($(this));
        });
    };
})(jQuery);
