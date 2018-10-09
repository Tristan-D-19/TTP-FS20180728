import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table, CheckBox, Form, FormGroup, Input, Modal, ModalBody, ModalFooter, ModalHeader, Label} from 'reactstrap';
import { Link } from 'react-router-dom';
import {buyStock, getAllStocks} from '../utils/APIHelper'

class StockList extends Component {


  constructor(props) {
    super(props);
    this.state = {stocks: [], isLoading: true, symbol:"", shares:"", purchased:"",  modal: false
    // , user:this.props.currentUser
  };
    this.onSubmit = this.onSubmit.bind(this);
    this.toggle = this.toggle.bind(this);
    this.onclick = this.onclick.bind(this);
    this.close = this.close.bind(this);

  }

  toggle(symbol) {   
    console.log(symbol)
    this.setState({
      modal: !this.state.modal,
      symbol:symbol
    });
  
   
  }

  close(){
    this.setState({
      modal: !this.state.modal,
  })
}
  onclick(symbol){ 
   
   
    this.setState({symbol:symbol})
  }

  componentDidMount() {
    // console.log("state user", this.state.user);
    this.setState({isLoading: true});
   getAllStocks()
      .then(response => this.setState({stocks:response, isLoading:false}))
      
  }

   onSubmit (event) {
    event.preventDefault()
    console.log("submit")
  const {symbol, shares} = this.state;
  const volume = shares;
  // const user = this.props.currentUser;
   if (symbol && shares) {
    buyStock(symbol, volume).then(response => {
      console.log(response);
    }).catch(error=>{
      console.log("fail")
    })
//    const response =  fetch(`/api/stocks/buy`, {
//             method: 'POST',
//             headers: {
//               'Accept': 'application/json',
//               'Content-Type': 'application/json'
//             },
//             body: JSON.stringify( {symbol:symbol, volume:volume}),
//           }).then(() => {
// console.debug(response);
//           });
//     }

  }
}

  render() {
    const {stocks, isLoading} = this.state;
    const {isAuthenticated} = this.props;
   let buyStock;
    if(isAuthenticated){
      buyStock = <Button onClick={this.toggle.bind(this, this.state.symbol)}>Buy</Button>
    }

    if (isLoading) {
      return <p>Loading...</p>;
    }
    const externalCloseBtn = <button className="close" style={{ position: 'absolute', top: '15px', right: '15px' }} onClick={this.close}>&times;</button>;
    
    const stockList = stocks.map(stock => {
      const price = `${stock.lastSalePrice || ''}`;
      const volume = `${stock.volume || ''}`
      const symbol =  `${stock.symbol || ''}`;
      return <tr  key={stock.symbol}>
        <td style={{whiteSpace: 'nowrap'}}>{symbol}</td>
        <td>{price}</td>
        <td>{volume}</td>
        <td>
      
  
        <Button onClick={this.toggle.bind(this,symbol)}>Buy</Button>
                      
             <Modal isOpen={this.state.modal} toggle={this.toggle} className="modal-display" external={externalCloseBtn}>
             <form onSubmit={this.onSubmit}>    
          <ModalHeader toggle={this.close}>Buy Shares</ModalHeader>  
           
          <Input type="hidden" name="symbol" className="symbol-button" value={this.state.symbol}></Input>
        
          <ModalBody>
          <Label for="shares" className="mt-4" sm={2}>Purchase Shares of {this.state.symbol}</Label> 
         
            <Input type="text" name="volume" id="shares" placeholder="Shares" value={this.state.shares}  onChange={e => this.setState({shares:e.target.value})}/>
           
          </ModalBody>
          <ModalFooter>    
            
            <Button color="primary"  type="submit" onClick={this.onSubmit} >Purchase</Button>
            <Button color="secondary" type="button" onClick={this.close}>Cancel</Button>
           
          </ModalFooter>
          </form>
        </Modal>
          
        </td>
      </tr>
    });

    return (
      <div>
      
        <Container fluid>
          <div className="float-right">

          </div>
          <h3>Stock Market</h3>
          <Table hover className="mt-4">
            <thead>
            <tr>
              <th width="20%">Symbol</th>
              <th width="20%">Price</th>
              <th>Shares</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {stockList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default StockList;