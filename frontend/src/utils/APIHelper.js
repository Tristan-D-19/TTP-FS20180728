import { API_BASE_URL, STOCK_LIST_SIZE, ACCESS_TOKEN } from '../constants';

//Helper class to make API Calls
const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json()
        .then(json => {
            if(!response.ok) {
                return Promise.reject(json); 
            }
            return json;
        })
    );
};

export function getAllStocks(page, size) {
    page = page || 0;
    size = size || STOCK_LIST_SIZE;
    console.log(page);
    return request({
        url: API_BASE_URL + `/stocks/all?page=${page}&size=${size}`,
        method: 'GET'
    });
}

export function buyStock(symbol, volume) {
    return request({
        url: API_BASE_URL + "/stocks/buy",
        method: 'POST',
        body: JSON.stringify({symbol:symbol, volume:volume})         
    });
}



export function login(email, password) {
    return request({
        url: API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify({email:email, password:password})
    });
}

export function register(name, email, password) {
    return request({
        url: API_BASE_URL + "/auth/register",
        method: 'POST',
        body: JSON.stringify({name:name, email:email, password:password})
    });
}


export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/token",
        method: 'GET'
    });
}

export function getUserProfile() {
    return request({
        url: API_BASE_URL + "/users/user",
        method: 'GET'
    });
}

export function getUserAccount(id){
    return request({
        url: API_BASE_URL + ""
    })
}

export function getUserTransactions() {
   
    return request({
        url: API_BASE_URL + "/users/user/transactions",
        method: 'GET'
    });
}

export function check(symbol){

    return request({
        url: `https://api.iextrading.com/1.0/stock/${symbol}/quote`
    , method: 'GET'
    })
}