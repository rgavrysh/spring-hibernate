angular.module('admin', ['auth'])
    .controller('admin', admin);

//todo: move logic to service
function admin($http, auth){
    var self = this;

    self.isAdmin = function(){
            if(auth.authenticated){
                auth.isAdmin(auth.currentUser, function(admin){
                    self.isAdmin = admin;
                })
            } else {
                self.isADmin = false;
            }
        }

    self.getUsersData = function() {
        if (self.isAdmin){
          $http.get('/customers').then(function(response){
            self.users = response.data;
            document.getElementById('progressbar').style.display='none';
          })
        } else {

        }
      }
      self.deleteUser = function (userInfo){
        if (confirm('Are you sure you want to delete user ' + userInfo.name + '?')){
          $http.delete('/customer/' + userInfo.id + '/delete').then(function(response){
            self.isRemoved = true;
            self.getUsersData();
          })
        }
      }

    self.isExpanded = false;
    self.userInfo = {
        name: "",
        phone: "",
        email: ""
    }

    self.showForm = function(){
        self.isExpanded = !self.isExpanded;
    }

    self.save = function(userInfo){
        $http.post('/customer', userInfo).then(function(response){
            self.isUserAddedSuccessfully = true;
        })
    }


}