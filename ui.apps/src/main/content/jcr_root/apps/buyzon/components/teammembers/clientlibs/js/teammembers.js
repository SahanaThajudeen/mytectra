(function(document, $, Coral) {
    "use strict";

    $(document).on("dialog-ready", function() {
        // Fix nested multifield initialization
        $(".cq-dialog-nested").each(function() {
            let $nested = $(this);
            let $multifields = $nested.find("[data-cq-dialog-nested]");

            $multifields.each(function() {
                let $mf = $(this);
                $mf.on("change", function() {
                    Coral.commons.ready($mf.get(0), function() {
                        $mf.trigger("foundation-contentloaded");
                    });
                });
            });
        });
    });
})(document, Granite.$, Coral);
