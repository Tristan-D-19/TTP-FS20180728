import React, { Component } from 'react';
import { Button, Table, Col,  FormControl, Label } from 'react-bootstrap';
import {Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap'
import {buyStock, getAllStocks} from '../utils/APIHelper'
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';


// StockList component: List of todays stocks, users may purchase stocks here
class StockList extends Component {


  constructor(props) {
    super(props);
    this.state = {stocks: [], isLoading: true, symbol:"", shares:"", purchased:"",  
    modal: false, totalPages:null, totalStocks: null,
    page:0, limit:50, backwards:false
  };
    this.onSubmit = this.onSubmit.bind(this);
    this.toggle = this.toggle.bind(this);
    this.onclick = this.onclick.bind(this);
    this.close = this.close.bind(this);
    this.nextPage = this.nextPage.bind(this);
    this.prevPage = this.prevPage.bind(this);
    this.goToPage = this.goToPage.bind(this);
    this.pageItem = this.pageItem.bind(this);
    this.changePageDir = this.changePageDir.bind(this);
  }

  //toggle a modal
  toggle(symbol) {   
    this.setState({
      modal: !this.state.modal,
      symbol:symbol
    });
  }

  //reverse the paging direction once a user hits the end
  changePageDir(){
    this.setState({backwards:true})  
  }
  pageItem (page, item){
    let pageCursor = page + item
    return <PaginationItem
    onClick={this.setState({page:pageCursor})}
    >{page + item}</PaginationItem>
   }

  //pull up the next page of stocks
nextPage(){
  let {page} = this.state;
  this.setState({page:++page})
  getAllStocks(this.state.page, this.state.limit)
  .then(response => this.setState({stocks:response.content, isLoading:false}))
  .catch(error=>{
    console.log(error);
    this.setState({isLoading:true})})
}
//pull up the previous page of stocks
prevPage(){
  let {page} = this.state;
  this.setState({page:--page})
  getAllStocks(this.state.page, this.state.limit)
  .then(response => this.setState({stocks:response.content, isLoading:false}))
  .catch(error=>{
    console.log(error);
    this.setState({isLoading:true})})
}

//go to a page that the user has clicked from pagination
goToPage(pagePointer){
 console.log(pagePointer)
  this.setState({page:pagePointer})
  console.log(this.state.page)
  getAllStocks(pagePointer, this.state.limit)
  .then(response => this.setState({stocks:response.content, isLoading:false}))
  .catch(error=>{
    console.log(error);
    this.setState({isLoading:true})})
}
//Close a modal
  close(){
    this.setState({
      modal: !this.state.modal,
  })
}

//set the stocks symbol to state
onclick(symbol){    
    this.setState({symbol:symbol})
  }

  componentDidMount() {
    this.setState({isLoading: true});

   getAllStocks(this.state.page, this.state.limit)
      .then(response => {
        console.log(response)
      this.setState({stocks:response.content, isLoading:false,
      totalPages:response.totalPages,totalStocks:response.totalElements
      })
    }
      )
      .catch(error=>console.log(error))  
  }
//handle stock purchase event 
   onSubmit (event) {
    event.preventDefault()
  const {symbol, shares} = this.state;
  const volume = shares;
   if (symbol && shares) {
    buyStock(symbol, volume).then(response => {
      console.log(response);
      this.setState({ modal: !this.state.modal})
      getAllStocks(this.state.page, this.state.limit)
        .then(response => this.setState({stocks:response.content, isLoading:false, shares:null}))
        .catch(error=>this.setState({isLoading:true}))
  
    }).catch(error=>{
      console.log("failed to purchase stock")
    })
  }
}

  render() {
    const {stocks, isLoading,totalPages} = this.state;
    let {page} = this.state;
    const {isAuthenticated} = this.props;
    let stockPaginationItems
    let prevPage = 
    <PaginationItem>
      <PaginationLink previous href="#" onClick={this.prevPage}  />
    </PaginationItem>

    let firstPage = <PaginationItem>
    <PaginationLink href="#" onClick={e =>{
      this.setState({backwards:false})
      this.goToPage(0)}}>
      {1}
    </PaginationLink>
   </PaginationItem> 

    let nextPage =  <PaginationItem>
    <PaginationLink href="#" onClick={e =>this.goToPage(totalPages)}>
      {totalPages}
    </PaginationLink>
  </PaginationItem>

//handle pagination for first page
  if (page ===0){
    firstPage = null
    prevPage = null
    
  }
  //handle last page for pagination
    if(totalPages){

      if(page===totalPages || this.state.backwards){
        
        nextPage = null
        stockPaginationItems = <div style={{display: "contents"}}> 
        <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page-5
           this.changePageDir()
           this.goToPage(p)}
        }>
           {page-5}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page-4
           this.changePageDir()
           this.goToPage(p)}}>
           {page-4}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page-3
           this.changePageDir()
           this.goToPage(p)}}>
           {page-3}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page-2
           this.changePageDir()
           this.goToPage(p)}}>
           {page-2}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page-1
           this.changePageDir()
           this.goToPage(p)}}>
           {page-1}
         </PaginationLink>
       </PaginationItem>
     </div>

      
      }
      else {
        stockPaginationItems = <div style={{display: "contents"}}> 
        <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page+1
          
           this.goToPage(p)}}>
           {page+1}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#"onClick={e =>{
           let p = page+2
           this.goToPage(p)}}>
           {page+2}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page+3
           this.goToPage(p)}}>
           {page+3}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page+4
           this.goToPage(p)}}>
           {page+4}
         </PaginationLink>
       </PaginationItem>
       <PaginationItem>
         <PaginationLink href="#" onClick={e =>{
           let p = page+5
           this.goToPage(p)}}>
           {page+5}
         </PaginationLink>
       </PaginationItem>
     </div>
      }

    }

    if(isAuthenticated){
      buyStock = <Button onClick={this.toggle.bind(this, this.state.symbol)}>Buy</Button>
    }

    if (isLoading) {
      return <p>Loading...</p>;
    }
    const externalCloseBtn = <button className="close" style={{ position: 'absolute', top: '15px', right: '15px' }} onClick={this.close}>&times;</button>;
    
    const stockList = stocks.map(stock => {
      const price = `${stock.lastSalesPrice || ''}`;
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
           
          <FormControl type="hidden" name="symbol" className="symbol-button" value={this.state.symbol}></FormControl>
        
          <ModalBody>
          <Label for="shares" className="mt-4" sm={2}>Purchase Shares of {this.state.symbol}</Label> 
         
            <FormControl type="text" name="volume" id="shares" placeholder="Shares" value={this.state.shares}  onChange={e => {this.setState({shares:e.target.value})
            }  
          }/>
           
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
      
        <Col fluid>
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
         
        </Col>
      
        <Pagination aria-label="Page navigation example">
        {prevPage}
        {firstPage}
      
        {stockPaginationItems}

        <PaginationItem>
          <PaginationLink next href="#" onClick={this.nextPage}/>
        </PaginationItem>
       {nextPage}
      </Pagination>
        
      </div>
    );
  }
}

export default StockList;