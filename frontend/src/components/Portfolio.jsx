import React, { Component } from 'react';
import { Button, ButtonGroup, Grid, Container, Table, CheckBox, Form, Col, Row, Jumbotron, FormGroup, ControlLabel, Modal, ModalBody, ModalFooter, FormControl, Label} from 'react-bootstrap';
import {getUserProfile, buyStock} from '../utils/APIHelper'
import './Portfolio.css';
export default class Profile extends Component {

  constructor(props) {
    super(props);
    this.state = {stocks: [], isLoading: true, symbol:"", shares:null, purchased:"",  modal: false, user:null,profile:null, 
    symbolError:{validationState:"", message:""}, shareError:{validationState:"", message:""}  
};
this.onSubmit = this.onSubmit.bind(this);
    
  }
 
  componentDidMount() {
    // console.log("state user", this.state.user);
    this.setState({isLoading: true});
   getUserProfile()
      .then(response => this.setState({profile:response, isLoading:false, stocks:response.stocks}))
  }

  componentWillReceiveProps(nextProps) {
      if(nextProps.currentUser)
      {this.setState({user:nextProps.currentUser})
    console.log(nextProps.currentUser)
      getUserProfile().then(
        response => {
            console.log(response)
            this.setState({profile:response, stocks:response.stocks})
          }
      ).catch(error=>{console.log(error)})
   } 
}

onSubmit(event){
event.preventDefault()

const {symbol, shares} = this.state;
console.log("submitting", shares);
if(symbol && shares)
buyStock(symbol, shares)
.then(response => {
    console.log(response)
    getUserProfile().then(
        response => {
            console.log(response)
            this.setState({profile:response, stocks:response.stocks})
          }
      ).catch(error=>{console.log(error)})
})
.catch(error => {

})
}

  render (){
    //   const profile = this.props.loadProfile();
    let profile = this.state.profile
    let stocks = this.state.stocks;
    let stockList;
    // console.log()
    if(!this.state.profile)
    profile = {};

    if(stocks)
    stockList = stocks.map(stock => {
        const price = `${stock.lastSalesPrice || ''}`;
        const volume = `${stock.volume || ''}`
        const symbol =  `${stock.symbol || ''}`;
        return <tr  key={stock.symbol}>
          <td style={{whiteSpace: 'nowrap'}}>{symbol}</td>
          <td>{price}</td>
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