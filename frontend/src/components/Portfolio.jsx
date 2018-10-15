import React, { Component } from 'react';
import { Button, Grid, Table, Col, Row, Jumbotron, FormGroup, ControlLabel, FormControl, Label} from 'react-bootstrap';
import {getUserProfile, buyStock} from '../utils/APIHelper'
import './Portfolio.css';

//Portfolio component: Displays a user's portfolio of stocks
export default class Portfolio extends Component {

  constructor(props) {
    super(props);
    this.state = {stocks: [], isLoading: true, symbol:"", shares:null, purchased:"",  modal: false, user:null,profile:null, 
    symbolError:{validationState:"", message:""}, shareError:{validationState:"", message:""}  
};
this.onSubmit = this.onSubmit.bind(this);
this.checkStock = this.checkStock.bind(this);
  }

  //get a stocks open price from IEXtrading API
  checkStock(symbol){
      let price
      const param = {
        headers:{'content-type': 'application/json; charset=UTF-8'},
        method:"GET",
    }
      const URL = `https://api.iextrading.com/1.0/stock/${symbol}/quote`
     price = fetch(URL, param).then(
        data=>{ return data.json()
        }
    ).then(response => {
        price = response.open     
        return price
    })    
    .catch(error=>{
        console.log(error)
    })
return price
}

//load user portfolio/profile
componentDidMount() { 
    this.setState({isLoading: true});
   getUserProfile()
      .then(response => this.setState({profile:response, isLoading:false, stocks:response.stocks}))
  }

componentWillReceiveProps(nextProps) {
      if(nextProps.currentUser)
      {this.setState({user:nextProps.currentUser})
    
      getUserProfile().then(
        response => {
           
            this.setState({profile:response, stocks:response.stocks})
          }
      ).catch(error=>{console.log(error)})
   } 
}

onSubmit(event){
event.preventDefault()
const {symbol, shares} = this.state;
if(symbol && shares)
buyStock(symbol, shares)
.then(response => {
    getUserProfile().then(
        response => {
            this.setState({profile:response, stocks:response.stocks})
          }
      ).catch(error=>{console.log(error)})
})
.catch(error => { console.log(error)
})
}

  render (){
   
    let profile = this.state.profile
    let stocks = this.state.stocks;
    let stockList;
    if(!this.state.profile)
    profile = {};
    //map each stock a table row
    if(stocks)
    stockList = stocks.map(stock => {
        const price = `${stock.lastSalesPrice || ''}`;
        const volume = `${stock.volume || ''}`
        const symbol =  `${stock.symbol || ''}`;
        let colorChange = "grey";
        let openPrice = this.checkStock(symbol)
        if(openPrice < price){
        colorChange = "green"
        
        }
        else if (openPrice > price){
        colorChange = "red"
        }        
        return <tr  key={stock.symbol}>
          <td style={{whiteSpace: 'nowrap',color: colorChange }}>{symbol}</td>
          <td className="stockPrice" id={symbol} style={{color: colorChange}} >{price}</td>
          <td>{volume}</td>
        </tr>
      });
  


return(
<Grid>
    <Row className="show-grid">
        <Col id="user-stocks">
            <Table sm={6} md={3}> 
                <thead>
                    <tr>
                        <th width="20%">Symbol</th>
                        <th width="20%">Price</th>
                        <th>Shares</th>
                    </tr>
                </thead> 
                <tbody>
            {stockList}
            </tbody>                  
            </Table>
        </Col>
        <Col>
        <div className="line"></div>
        </Col>
        <Col id="buy-stocks">
        <Jumbotron>
        <h5>
            Cash <Label>${profile.balance}</Label>
        </h5> 
        <form onSubmit={this.onSubmit}>
        <FormGroup row>
            <Label for="ticker" sm={2}>Ticker</Label>
            <Col sm={10}>
            <FormControl type="text" name="symbol" id="symbol" placeholder="Ticker"  
            onChange={e => this.setState({symbol:e.target.value})} 
            validationState={this.state.symbolError.validationState}
            />
            <FormControl.Feedback />
          <ControlLabel>{this.state.symbolError.message}</ControlLabel>
          <FormControl type="text" name="shares" id="shares" placeholder="Qnty"  
            onChange={e => this.setState({shares:e.target.value})} 
            validationState={this.state.shareError.validationState}
            />
            <FormControl.Feedback />
          <ControlLabel>{this.state.shareError.message}</ControlLabel>
          
            </Col>
        </FormGroup>
        <Button type="submit" onClick={this.onSubmit}> Buy </Button>
        </form>
        </Jumbotron>
        </Col>
    </Row>
</Grid>);
  }
}