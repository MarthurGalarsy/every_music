$(function () {
    $(document).on("click", "#logout", function (event) {
        event.preventDefault();

        $.post("/api/member/logout")
            .always(() => {
                location.href = "/";
            });
    });
});
