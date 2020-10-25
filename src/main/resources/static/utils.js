function onEditClicked(userId) {
     window.location.href = '/users/' + userId;
}

$(document).ready(() => {

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
    })
});
