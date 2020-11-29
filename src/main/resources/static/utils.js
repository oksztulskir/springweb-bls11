function onEditClicked(userId) {
     window.location.href = '/users/' + userId;
}

function logout() {
    window.location.href = '/auth/logout';
}

function showOrHideSuccessModal(successMsg) {
    return successMsg.length > 0;
}

function initForm() {
    $('#successModal')
    .toggle(showOrHideSuccessModal($('#successMsg').val()));
}

function closeModal() {
    $('#successModal').toggle(false);
}

function createUserOnClick() {
    window.location.href = '/users/create';
}

$(document).ready(() => {
    initForm();
    $(".button-delete").click((event) => {
        console.info('Element clicked with id: ', event.target.id);
        const id = event.target.getAttribute('data-id');
        $.ajax(
            {
                url: '/rest/users/' + id,
                method: 'DELETE'
            }
        )
        .then(() => {
            console.log('Element was deleted: ', id);
            window.location.href = '/users';
        })
        .catch((error) => {
            console.error('Error during user deletion: ', error);
        });
    });



});
